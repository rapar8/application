package com.mywarehouse.service;


import com.mywarehouse.entity.Article;
import com.mywarehouse.entity.Product;
import com.mywarehouse.entity.ProductArticle;
import com.mywarehouse.entity.ProductArticlePK;
import com.mywarehouse.exceptoin.StockNotAvailableException;
import com.mywarehouse.model.ContainArticle;
import com.mywarehouse.model.Products;
import com.mywarehouse.repository.ArticleRepository;
import com.mywarehouse.repository.AvailableProductRepository;
import com.mywarehouse.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final UtilityService utilityService;

    private final ProductRepository productRepository;
    private final ArticleRepository articleRepository;
    private final AvailableProductRepository availableProductRepository;

    public ProductService(UtilityService utilityService, ProductRepository productRepository, ArticleRepository articleRepository, AvailableProductRepository availableProductRepository) {
        this.utilityService = utilityService;
        this.productRepository = productRepository;
        this.articleRepository = articleRepository;
        this.availableProductRepository = availableProductRepository;
    }

    @Transactional
    public void loadFile(@RequestParam(value = "file") MultipartFile file) throws IOException {

        List<Product> productListToUpload = model2Entity(utilityService.getModel(file, Products.class));

        getProductIdIfProductExistsFromDB(productListToUpload);

        productRepository.saveAll(productListToUpload);

        productRepository.refreshNeededArticle();
    }

    /**
     * As file do not include ID of the product, We need to get ID to update product from file
     *
     * @param productListToUpload - new product from file to be uploaded
     */
    private void getProductIdIfProductExistsFromDB(List<Product> productListToUpload) {
        List<String> productsName = productListToUpload.stream().map(Product::getName).toList();

        List<Product> existingProducts = productRepository.findByNameIn(productsName);

        for (Product exitingProduct : existingProducts) {
            for (Product newProduct : productListToUpload) {
                if (exitingProduct.getName().equals(newProduct.getName())) {
                    newProduct.setId(exitingProduct.getId());
                    break;
                }
            }
        }
    }

    public void sell(String byId) {

        Product product = getProductArticle(byId);

        List<Article> availableArticle = checkIfStockAvailable(product.getId(), product.getProductArticles());

        if (availableArticle != null)
            updateInventory(product.getProductArticles(), availableArticle);
        else
            throw new StockNotAvailableException(product.getName() + ":Stock not Available");

        // Any further notification or push to next messaging e.g. pub/sub
    }

    private void updateInventory(List<ProductArticle> productArticles, List<Article> availableArticle) {
        // productArticles.stream().map(productArticle -> {
        for (ProductArticle productArticle : productArticles) {

            Integer artId = productArticle.getProductArticlePK().getArticleId().getArtId();

            for (Article article : availableArticle) {
                if (Objects.equals(article.getArtId(), artId)) {
                    article.setStock(article.getStock() - productArticle.getAmountOf());
                    break;
                }
            }// below is less efficient than above
            /*availableArticle.stream().forEach( article -> {
                    if (article.getArtId() == artId) {
                        article.setStock(article.getStock() - productArticle.getAmountOf());
                    }
            });*/
//            return productArticle;
//        });
        }
        articleRepository.saveAll(availableArticle);
        articleRepository.refreshAvailableArticle();
    }

    private List<Article> checkIfStockAvailable(Integer productId, List<ProductArticle> productArticlesCompareAgainst) {

        List<Article> actualArticleStock = articleRepository.findByJoinWithProductArticle(productId);

        if (productArticlesCompareAgainst.size() == actualArticleStock.size()) {
            //Product Eligible to sell
            return actualArticleStock;
        }
        return null;
    }

    private Product getProductArticle(String id) {
        Optional<Product> p = utilityService.isNumeric(id) ?
                productRepository.findById(Integer.valueOf(id)) :
                productRepository.findByName(id);

        return p.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not Found"));

    }

    private List<Product> model2Entity(Products products) {
        List<Product> productEntities = new ArrayList<>();
        List<ProductArticle> productArticleEntityList;

        ProductArticle productArticleEntity;
        Product productEntity;

        for (com.mywarehouse.model.Product product : products.products()) {
            productEntity = new Product();
            productArticleEntityList = new ArrayList<>();

            productEntity.setName(product.name());
            Product finalProductEntity = productEntity;


            for (ContainArticle containArticle : product.contain_articles()) {
                productArticleEntity = new ProductArticle();

                ProductArticlePK productArticlePK = new ProductArticlePK();
                productArticlePK.setProductId(productEntity);

                productArticlePK.setArticleId(new Article(Integer.valueOf(containArticle.getArtId()), null, null));
                productArticleEntity.setAmountOf(Integer.valueOf(containArticle.getAmountOf()));

                productArticleEntity.setProductArticlePK(productArticlePK);

                productArticleEntityList.add(productArticleEntity);
            }

            finalProductEntity.setProductArticles(productArticleEntityList);

            productEntities.add(finalProductEntity);
        }
        return productEntities;
    }

    public List<Product> getAvailableProduct() {
        return (List<Product>) productRepository.findAllById(availableProductRepository.availableProduct());
    }

}
