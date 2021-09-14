drop schema if exists testravi CASCADE;
create schema testravi;

SET search_path TO testravi;

drop table if exists product CASCADE;
create table product
(
    id integer generated always as identity constraint product_pid primary key,
    name text
) ;

drop table if exists article CASCADE;
create table article
(
    id    integer constraint article_pid primary key,
    name  text,
    stock int check ( stock > -1 )
) ;

drop table if exists product_article;
create table product_article
(
    product_id integer constraint f_product_id references product (id),
    article_id integer constraint f_article_id references article (id),
    amount_of int
 ) ;

CREATE MATERIALIZED VIEW IF NOT EXISTS needed_article as select  product_id, count(product_id) as count from product_article group by product_id;
CREATE MATERIALIZED VIEW IF NOT EXISTS available_article as select product_id, count(product_id) as count from article a inner join  product_article p
                            on  a.id = p.article_id and a.stock >= p.amount_of group by product_id;

CREATE UNIQUE INDEX productId_available_article ON available_article (product_Id);
CREATE UNIQUE INDEX productId_needed_article ON needed_article (product_Id);

REFRESH MATERIALIZED VIEW CONCURRENTLY needed_article;
REFRESH MATERIALIZED VIEW CONCURRENTLY available_article;