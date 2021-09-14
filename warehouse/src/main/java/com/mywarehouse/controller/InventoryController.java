package com.mywarehouse.controller;

import com.mywarehouse.annotation.InventoryRequestMapping;
import com.mywarehouse.service.InventoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@InventoryRequestMapping
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping(value = "upload")
    @ResponseStatus(CREATED)
    @Transactional
    public void uploadInventories(@RequestParam(name = "file")  MultipartFile file) throws IOException {

        inventoryService.loadFile(file);
    }
}
