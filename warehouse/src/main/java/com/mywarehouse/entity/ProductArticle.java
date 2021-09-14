package com.mywarehouse.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
@Setter
@Getter
public class ProductArticle implements Serializable {

    @EmbeddedId
    private ProductArticlePK productArticlePK;

    private Integer amountOf;
}
