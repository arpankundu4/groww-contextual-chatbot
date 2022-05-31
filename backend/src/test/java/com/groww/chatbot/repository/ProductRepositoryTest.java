package com.groww.chatbot.repository;

import com.groww.chatbot.model.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * unit tests
 * for product repository
 */

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository underTest;

    private Product product;

    private List<Product> products;

    private List<String> productIds;

    @BeforeAll
    void beforeAll() {
        // create test product
        product = new Product("product1",
                              "product",
                              "category1",
                               1000,
                              "logo.png",
                              "chart.png");

        // products list
        products = List.of(product);

        // product ids
        productIds = List.of(product.getId());
    }

    @Test
    void checkIfReturnsProductsList_whenAllProductsFoundById() {
        // given
        underTest.saveAll(products);
        List<Product> expectedProducts = products;

        // when
        List<Product> resultProducts = underTest.findAllById(productIds);

        // then
        assertThat(resultProducts).isEqualTo(expectedProducts);
    }

    @Test
    void checkIfReturnsEmptyList_whenNoProductsFoundById() {
        // given
        List<Product> expectedProducts = Collections.emptyList();

        // when
        List<Product> resultProducts = underTest.findAllById(Collections.emptyList());

        // then
        assertThat(resultProducts).isEqualTo(expectedProducts);
    }

    @AfterAll
    void afterAll() {
        underTest.deleteAll(products);
    }
}