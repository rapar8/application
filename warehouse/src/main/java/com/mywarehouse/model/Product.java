package com.mywarehouse.model;

import java.util.List;

/**
 * This can be generated using jsonschema2pojo OR org.openapitools plugins
 * if json schema is available like Swagger schema
 */
public record Product
        (String name,
         List<ContainArticle> contain_articles) {
}







