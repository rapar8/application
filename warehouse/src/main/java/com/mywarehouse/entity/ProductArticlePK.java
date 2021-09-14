package com.mywarehouse.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
public class ProductArticlePK implements Serializable {

        @ManyToOne
        @JoinColumn(name = "product_id", referencedColumnName = "id")
        private Product productId;
        @ManyToOne
        @JoinColumn(name = "article_id", referencedColumnName = "id")
        private Article articleId;
}
