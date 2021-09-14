package com.mywarehouse.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTestIT {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(2)
    void uploadProducts() throws Exception {
        var file = new MockMultipartFile("file", "filename.txt", "text/plain", ("" +
                """
                        {"products":[{"name":"Dining Chair","contain_articles":[{"art_id":"1","amount_of":"4"},{"art_id":"2","amount_of":"8"},{"art_id":"3","amount_of":"1"}]},{"name":"Dinning Table","contain_articles":[{"art_id":"1","amount_of":"4"},{"art_id":"2","amount_of":"8"},{"art_id":"4","amount_of":"1"}]}]}""").getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/products/upload")
                        .file(file)
                        .header("content-type", "multipart/form-data")
//                        .with(SecurityMockMvcRequestPostProcessors.authentication(token)))
        ).andExpect(status().isCreated());
    }

    @Test
    @Order(1)
    void uploadInventories() throws Exception {
        var file = new MockMultipartFile("file", "filename.txt", "text/plain", ("" +
                """
                        {"inventory":[{"art_id":"1","name":"leg","stock":"12"},{"art_id":"2","name":"screw","stock":"17"},{"art_id":"3","name":"seat","stock":"2"},{"art_id":"4","name":"table top","stock":"1"}]}""").getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/inventories/upload")
                        .file(file)
                        .header("content-type", "multipart/form-data")
//                        .with(SecurityMockMvcRequestPostProcessors.authentication(token)))
        ).andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    void sellProduct() throws Exception {

        mvc.perform(MockMvcRequestBuilders.put("/products/sell/1")
        ).andExpect(status().isAccepted());

        mvc.perform(MockMvcRequestBuilders.put("/products/sell/1")
        ).andExpect(status().isAccepted());
    }

    @Test
    @Order(5)
    void sellProduct_noStockFound() throws Exception {

        mvc.perform(MockMvcRequestBuilders.put("/products/sell/1")
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    void getId() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/products/get")
        ).andExpect(status().isOk());
    }
}