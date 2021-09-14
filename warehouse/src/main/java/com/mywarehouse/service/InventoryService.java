package com.mywarehouse.service;

import com.mywarehouse.model.Inventories;
import com.mywarehouse.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class InventoryService {
    private final UtilityService utilityService;

    private final ArticleRepository articleRepository;

    public InventoryService(UtilityService utilityService, ArticleRepository articleRepository) {
        this.utilityService = utilityService;
        this.articleRepository = articleRepository;
    }

    public void loadFile(MultipartFile file) throws IOException {

        Inventories inventories = utilityService.getModel(file, Inventories.class);

        articleRepository.saveAll(inventories.inventory());

        articleRepository.refreshAvailableArticle();
    }
}
