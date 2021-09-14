package com.mywarehouse.model;

import com.mywarehouse.entity.Article;

import java.util.List;

/**
 * This can be generated using jsonschema2pojo OR org.openapitools plugins
 * if json schema is available like Swagger schema
 */
public record Inventories(List<Article> inventory){ }
