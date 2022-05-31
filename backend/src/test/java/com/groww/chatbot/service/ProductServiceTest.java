package com.groww.chatbot.service;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.model.Product;
import com.groww.chatbot.model.ProductCategory;
import com.groww.chatbot.repository.ProductCategoryRepository;
import com.groww.chatbot.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * unit tests
 * for product service
 */

@DataMongoTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductServiceImpl underTest;

    private Product product;

    private ProductCategory productCategory;

    @BeforeAll
    void beforeAll() {
        // create product category
        productCategory = new ProductCategory("category1",
                                              "title",
                                              List.of("product1"));

        // create test product
        product = new Product("product1",
                              "product",
                              "category1",
                               1000,
                              "logo.png",
                              "chart.png");
    }

    @Test
    @SuppressWarnings("unchecked")
    void checkIfGetsProducts_whenValidCategoryId() throws NotFoundException {
        // given
        String categoryId = "category1";
        // mock findById to return productCategory
        given(productCategoryRepository
                .findById(categoryId))
                .willReturn(Optional.of(productCategory));

        // when
        underTest.getProducts(categoryId);

        // then
        // capture product ids list
        ArgumentCaptor<List<String>> productIdsCaptor = ArgumentCaptor.forClass(List.class);
        verify(productRepository).findAllById(productIdsCaptor.capture());
        // compare with actual product ids list
        assertThat(productIdsCaptor
                .getValue())
                .isEqualTo(productCategory.getProductIds());
    }

    @Test
    void checkIfThrowsException_whenInvalidCategoryId() {
        // given
        String categoryId = "category2";
        // mock findById to return empty response
        given(productCategoryRepository
                .findById(categoryId))
                .willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest
                .getProducts(categoryId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Product category not found");

        // verify findById is not invoked after this
        verify(productRepository, never()).findAllById(any());
    }

    @Test
    void checkIfGetsProduct_whenValidProductId() throws NotFoundException {
        // given
        String productId = product.getId();
        Product expectedProduct = product;
        // mock findById
        given(productRepository
                .findById(productId))
                .willReturn(Optional.of(expectedProduct));

        // when
        Product resultProduct = underTest.getProduct(productId);

        // then
        assertThat(resultProduct).isEqualTo(expectedProduct);
    }

    @Test
    void checkIfThrowsException_whenInvalidProductId() {
        // given
        String productId = "product2";
        // mock findById to return empty response
        given(productRepository
                .findById(productId))
                .willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest
                .getProduct(productId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Product not found");
    }

}