package com.sid.product_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.product_service.dto.ProductRequest;
import com.sid.product_service.dto.ProductResponse;
import com.sid.product_service.model.Product;
import com.sid.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;
//    @Autowired
//    private void objectMapper(ObjectMapper objectMapper){
//        this.objectMapper=objectMapper;
//    }

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.9");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }


    @Test
    void shouldCreateProduct() throws Exception {

        ProductRequest productRequest = getProductRequest();
        String string = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product").contentType(MediaType.APPLICATION_JSON).content(string)).andExpect(status().isCreated());
        Assertions.assertEquals(1, productRepository.findAll().size());

    }

    @Test
    public void shouldGetProductList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder().name("Samsung s12").price(BigDecimal.valueOf(120000)).description("Samsung s12").build();
    }

    private ProductResponse getProductResponse(Product product) {
        return ProductResponse.builder().name(product.getName()).id(product.getId()).description(product.getDescription()).price(product.getPrice()).build();
    }


}
