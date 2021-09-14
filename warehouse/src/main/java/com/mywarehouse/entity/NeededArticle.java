package com.mywarehouse.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Getter
@Setter
public class NeededArticle {
    @Id
    Integer productId;
    Integer count;
}
