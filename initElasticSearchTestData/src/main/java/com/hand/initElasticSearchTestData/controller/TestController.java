package com.hand.initElasticSearchTestData.controller;

import com.hand.initElasticSearchTestData.config.ElasticsearchProperties;
import com.hand.initElasticSearchTestData.util.ElasticSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class
 *
 * @author Vivianus
 * @date 2018/02/28
 **/

@RestController
public class TestController {

    @Autowired
    ElasticsearchProperties elasticsearchProperties;

    @RequestMapping("/send")
    public String send() {

        ElasticSearch.getTransPortClient(
                elasticsearchProperties.getName(),
                elasticsearchProperties.getIp(),
                elasticsearchProperties.getPort(),
                elasticsearchProperties.getIndexNum(),
                elasticsearchProperties.getJsonNum());

        return "sent ";
    }
}
