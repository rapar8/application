package com.mywarehouse.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
class InventoryControllerTestIT {
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
}