package com.hand.initElasticSearchTestData.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * PropertiesConfig class
 *
 * @author Vivianus
 * @date 2018/02/28
 **/

@Component
public class ElasticsearchProperties {

    @Value("${es.name}")
    private String name;

    @Value("${es.ip}")
    private String ip;

    @Value("${es.port}")
    private String port;

    @Value("${es.executeNum}")
    private Integer executeNum;

    @Value("${es.indexNum}")
    private Integer indexNum;

    @Value("${es.jsonNum}")
    private Integer jsonNum;

    @Value("${es.indexName}")
    private String indexName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getExecuteNum() {
        return executeNum;
    }

    public void setExecuteNum(Integer executeNum) {
        this.executeNum = executeNum;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }

    public Integer getJsonNum() {
        return jsonNum;
    }

    public void setJsonNum(Integer jsonNum) {
        this.jsonNum = jsonNum;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
