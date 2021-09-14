package com.mywarehouse.repository;

import com.mywarehouse.entity.AvailableArticle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableProductRepository extends CrudRepository<AvailableArticle, Integer> {

    @Query("select available.productId from AvailableArticle available, NeededArticle needed where available.productId = needed.productId and available.count = needed.count")
    List<Integer> availableProduct();
}
