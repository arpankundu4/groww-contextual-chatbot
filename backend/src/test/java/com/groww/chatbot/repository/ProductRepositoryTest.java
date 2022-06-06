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

    private String categoryId;

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

        // persist to db
        underTest.saveAll(products);
    }

    @Test
    void checkIfReturnsProductsList_whenProductsFoundByCategoryId() {
        // given
        categoryId = "category1";
        List<Product> expectedProducts = products;

        // when
        List<Product> resultProducts = underTest.findAllByCategoryId(categoryId);

        // then
        assertThat(resultProducts).isEqualTo(expectedProducts);
    }

    @Test
    void checkIfReturnsEmptyList_whenNoProductsFoundByCategoryId() {
        // given
        categoryId = "category2";
        List<Product> expectedProducts = Collections.emptyList();

        // when
        List<Product> resultProducts = underTest.findAllByCategoryId(categoryId);

        // then
        assertThat(resultProducts).isEqualTo(expectedProducts);
    }

    @Test
    void checkIfDoesNotDeleteProducts_whenProductsNotFoundByCategoryId() {
        // given
        categoryId = "category2";

        // when
        underTest.deleteAllByCategoryId(categoryId);

        // then
        assertThat(underTest
                .findAllByCategoryId(product.getCategoryId()))
                .isNotEqualTo(Collections.emptyList());
    }

    @Test
    void checkIfDeletesProducts_whenProductsFoundByCategoryId() {
        // given
        categoryId = "category1";

        // when
        underTest.deleteAllByCategoryId(categoryId);

        // then
        assertThat(underTest
                .findAllByCategoryId(categoryId))
                .isEqualTo(Collections.emptyList());
    }

    @AfterAll
    void afterAll() {
        underTest.deleteAll(products);
    }

}