package com.mywarehouse.repository;

import com.mywarehouse.entity.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
//public interface ArticleRepository extends ReactiveCrudRepository<Article, Integer> {

    @Query("select a from ProductArticle p inner join Article a on p.productArticlePK.articleId.artId = a.artId and a.stock >= p.amountOf and p.productArticlePK.productId.id = ?1")
    List<Article> findByJoinWithProductArticle(Integer productId);

    @Modifying
    @Query(value = "REFRESH MATERIALIZED VIEW available_article", nativeQuery = true)
    void refreshAvailableArticle();

}
