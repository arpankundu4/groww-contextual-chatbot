package com.groww.chatbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.AddProductRequest;
import com.groww.chatbot.exchanges.EditProductRequest;
import com.groww.chatbot.model.Product;
import com.groww.chatbot.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.groww.chatbot.config.ApiRoutes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * integration tests
 * for admin controller
 */

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(
        username = "admin@groww.in",
        authorities = "ADMIN"
)
class AdminIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private AddProductRequest validAddProductRequest;

    private EditProductRequest validEditProductRequest;

    @BeforeAll
    void beforeAll() {
        // valid add product request
        validAddProductRequest = new AddProductRequest(
                "product1",
                1000,
                "logo.png",
                "chart.png");

        // valid edit product request
        validEditProductRequest = new EditProductRequest(
                "product-new",
                null,
                100,
                null,
                null);
    }

    @Test
    void whenValidAddProductRequestAndCategoryId_thenAddProductReturns201() throws Exception {
        // given
        // valid category id
        // valid add product request

        // when
        when(productService
                .addProduct(any(AddProductRequest.class), anyString()))
                .thenReturn(new Product());

        // mock post request
        mockMvc.perform(post(ADD_PRODUCT_URL.replace("{categoryId}", "category1"))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(validAddProductRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenInvalidCategoryId_thenAddProductReturns404() throws Exception {
        // given
        // invalid category id
        // valid add product request

        // when
        doThrow(new NotFoundException("Category not found"))
                .when(productService)
                .addProduct(any(AddProductRequest.class), anyString());

        // mock post request
        mockMvc.perform(post(ADD_PRODUCT_URL.replace("{categoryId}", "category2"))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(validAddProductRequest)))
                .andExpect(status().isNotFound());

    }

    @Test
    void whenInvalidAddProductRequest_thenAddProductReturns400() throws Exception {
        // given
        // invalid add product request
        AddProductRequest invalidAddProductRequest = new AddProductRequest(
                "",
                100,
                "logo.png",
                null);

        // mock post request
        mockMvc.perform(post(ADD_PRODUCT_URL.replace("{categoryId}", "category1"))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(invalidAddProductRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenValidEditProductRequestAndProductId_thenEditProductReturns200() throws Exception {
        // given
        // valid product id
        // valid edit product request

        // when
        when(productService
                .editProduct(any(EditProductRequest.class), anyString()))
                .thenReturn(new Product());

        // mock post request
        mockMvc.perform(patch(EDIT_PRODUCT_URL.replace("{productId}", "product1"))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(validEditProductRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void whenInvalidProductId_thenEditProductReturns404() throws Exception {
        // given
        // invalid product id
        // valid edit product request

        // when
        doThrow(new NotFoundException("Product not found"))
                .when(productService)
                .editProduct(any(EditProductRequest.class), anyString());

        // mock post request
        mockMvc.perform(patch(EDIT_PRODUCT_URL.replace("{productId}", "product2"))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(validEditProductRequest)))
                .andExpect(status().isNotFound());

    }

    @Test
    void whenValidProductId_thenDeleteProductReturns200() throws Exception {
        // given
        // valid product id

        // when
        doNothing().when(productService).deleteProduct(anyString());

        // mock post request
        mockMvc.perform(delete(DELETE_PRODUCT_URL.replace("{productId}", "product1"))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
    @Test
    void whenInvalidProductId_thenDeleteProductReturns404() throws Exception {
        // given
        // invalid product id

        // when
        doThrow(new NotFoundException("Product not found"))
                .when(productService)
                .deleteProduct(anyString());

        // mock post request
        mockMvc.perform(delete(DELETE_PRODUCT_URL.replace("{productId}", "product2"))
                .contentType("application/json"))
                .andExpect(status().isNotFound());

    }

}