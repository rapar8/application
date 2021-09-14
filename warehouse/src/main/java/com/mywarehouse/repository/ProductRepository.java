package com.mywarehouse.repository;

import com.mywarehouse.entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    //public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Optional<Product> findByName(String name);

    List<Product> findByNameIn(List name);

    @Modifying
    @Query(value = "REFRESH MATERIALIZED VIEW needed_article; REFRESH MATERIALIZED VIEW available_article", nativeQuery = true)
    void refreshNeededArticle();
}
