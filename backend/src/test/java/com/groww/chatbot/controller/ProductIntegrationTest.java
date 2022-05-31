package com.groww.chatbot.controller;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.model.Product;
import com.groww.chatbot.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static com.groww.chatbot.config.ApiRoutes.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * integration tests
 * for product controller
 */

@SpringBootTest
@AutoConfigureMockMvc
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void whenValidCategoryId_thenReturns200() throws Exception {
        // valid category id
        String categoryId = "category1";

        // mock getProducts
        when(productService
                .getProducts(categoryId))
                .thenReturn(anyList());

        // mock get request
        mockMvc.perform(get(GET_PRODUCTS_URL, categoryId))
                .andExpect(status().isOk());
    }

    @Test
    void whenInvalidCategoryId_thenReturns404() throws Exception {
        // invalid category id
        String categoryId = "category2";

        // mock getProducts to throw exception
        when(productService
                .getProducts(categoryId))
                .thenThrow(new NotFoundException("Product category not found"));

        // mock get request
        mockMvc.perform(get(GET_PRODUCTS_URL, categoryId))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenValidProductId_thenReturns200() throws Exception {
        // valid product id
        String productId = "product1";

        // mock getProduct
        when(productService
                .getProduct(productId))
                .thenReturn(any(Product.class));

        // mock get request
        mockMvc.perform(get(GET_PRODUCT_URL, productId))
                .andExpect(status().isOk());
    }

    @Test
    void whenInvalidProductId_thenReturns404() throws Exception {
        // invalid product id
        String productId = "product2";

        // mock getProduct to throw exception
        when(productService
                .getProduct(productId))
                .thenThrow(new NotFoundException("Product not found"));

        // mock get request
        mockMvc.perform(get(GET_PRODUCT_URL, productId))
                .andExpect(status().isNotFound());
    }
}