package com.hand.initElasticSearchTestData.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * @author wangzhe
 */
public class ElasticSearch {

    private static Logger logger = LogManager.getLogger(ElasticSearch.class);

    public static void getTransPortClient(String esClusterName, String esServerIp, String esServerPort,
                                          Integer executeNum, Integer indexNum, Integer jsonNum) {
        Long startTime = System.currentTimeMillis();
        TransportClient client = null;

        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", esClusterName)
                    .build();

            client = new PreBuiltTransportClient(settings);

            String[] ipArray = esServerIp.split(",");
            String[] portArray = esServerPort.split(",");
            for (int i = 0; i < ipArray.length; i++) {

                client.addTransportAddress(new TransportAddress(InetAddress.getByName(ipArray[i]),
                        Integer.parseInt(portArray[i])));

                BulkRequestBuilder bulkRequest = client.prepareBulk();
                for (int x = 0; x < executeNum; x++) {
                    for (int z = 0; z < indexNum; z++) {
                        Integer zz = z;
                        for (int a = 0; a < jsonNum; a++) {
                            Integer ii = a;
                            String id = ii.toString();
                            String json = "{\"query\": {\"match\": {\"body\": {\"query\": \"ignore me\"}}}}";
                            IndexRequestBuilder indexRequest = client.prepareIndex(zz.toString(), "test").setId(id)
                                    .setSource(json, XContentType.JSON);
                            bulkRequest.add(indexRequest);
                        }
                    }
                    BulkResponse bulkResponse = bulkRequest.execute().actionGet();
                    if (bulkResponse.hasFailures()) {
                        logger.error("error!!!");
                    }
                }
            }
            client.close();
            logger.info("Use time: " + (System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            logger.error(e);
            if (client != null) {
                client.close();
            }
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }
}
