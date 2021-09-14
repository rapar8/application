package com.mywarehouse.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;


/**
 * This can be generated using jsonschema2pojo OR org.openapitools plugins
 * if json schema is available like Swagger schema
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@Setter
public class ContainArticle {
    String artId;
    String amountOf;
}

