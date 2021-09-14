package com.mywarehouse.controller;

import com.mywarehouse.annotation.ProductRequestMapping;
import com.mywarehouse.entity.Product;
import com.mywarehouse.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@ProductRequestMapping
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("upload")
    @ResponseStatus(CREATED)
    @Transactional
    public void uploadProducts(@RequestParam(name = "file") MultipartFile file) throws IOException {

        productService.loadFile(file);
    }

    @PutMapping(value = "sell/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity sellProduct(@PathVariable String id) {
        productService.sell(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("get")
    public List<Product> getId() {
        return productService.getAvailableProduct();
    }
}
