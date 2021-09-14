package com.mywarehouse.repository;

import com.mywarehouse.entity.ProductArticle;
import org.springframework.data.repository.CrudRepository;

public interface ProductArticleRepository extends CrudRepository<ProductArticle, Integer> {
//public interface ProductArticleRepository extends ReactiveCrudRepository<ProductArticle, Integer> {
}
