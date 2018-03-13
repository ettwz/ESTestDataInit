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
        Settings settings = Settings.builder()
                .put("cluster.name", esClusterName)
                .put("client.transport.ping_timeout", "30s")
                .put("client.transport.sniff", true)
                .build();
        String[] ipArray = esServerIp.split(",");
        String[] portArray = esServerPort.split(",");

        try {
            client = new PreBuiltTransportClient(settings);

            for (int i = 0; i < ipArray.length; i++) {
                client.addTransportAddress(new TransportAddress(InetAddress.getByName(ipArray[i]),
                        Integer.parseInt(portArray[i])));
                for (int x = 0; x < executeNum; x++) {
                    BulkRequestBuilder bulkRequest = client.prepareBulk();
                    bulkRequest.setTimeout("30s");
                    for (int z = 0; z < indexNum; z++) {
                        for (int a = 0; a < jsonNum; a++) {
                            String json = "{\"first_name\":\"John\",\"last_name\":\"Smith\",\"age\":25,\"about\":\"I love to go rock climbing\",\"interests\":[\"sports\",\"music\"]}";
                            IndexRequestBuilder indexRequest = client.prepareIndex(x + "-" + z, "test").setId(x + "-" + z + "-" + a)
                                    .setSource(json, XContentType.JSON);
                            bulkRequest.add(indexRequest);
                            logger.debug(x + "-" + z + "-" + a);
                        }
                    }
                    BulkResponse bulkResponse = bulkRequest.get();
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
