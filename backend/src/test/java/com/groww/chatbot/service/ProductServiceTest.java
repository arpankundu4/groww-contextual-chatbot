package com.groww.chatbot.service;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.AddProductRequest;
import com.groww.chatbot.exchanges.EditProductRequest;
import com.groww.chatbot.model.Category;
import com.groww.chatbot.model.Product;
import com.groww.chatbot.repository.CategoryRepository;
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
import java.util.function.Function;

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
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl underTest;

    private Product product;

    private Category category;

    @BeforeAll
    void beforeAll() {
        // create product category
        category = new Category("category1",
                                 null,
                                "title",
                                 false);

        // create test products
        product = new Product("product1",
                              "product",
                              "category1",
                               1000,
                              "logo.png",
                              "chart.png");

    }

    @Test
    void checkIfGetsProducts_whenValidCategoryId() throws NotFoundException {
        // given
        String categoryId = "category1";
        // mock findById
        given(categoryRepository
                .findById(categoryId))
                .willReturn(Optional.of(category));
        // mock findAllByCategoryId
        given(productRepository
                .findAllByCategoryId(categoryId))
                .willReturn(List.of(product));

        // when
        // then
        assertThat(underTest
                .getProducts(categoryId))
                .isEqualTo(List.of(product));
    }

    @Test
    void checkIfGetProductsThrowsException_whenInvalidCategoryId() {
        // given
        String categoryId = "category2";
        // mock findById to return empty response
        given(categoryRepository
                .findById(categoryId))
                .willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest
                .getProducts(categoryId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Category not found");

        // verify findAllByCategoryId is not invoked after this
        verify(productRepository, never()).findAllByCategoryId(any());
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

    @Test
    void checkIfProductAdded_whenValidCategoryId() throws NotFoundException {
        // given
        String categoryId = "category1";
        // create add product request
        AddProductRequest addProductRequest = new AddProductRequest(
                "product1",
                 1000,
                "logo.png",
                "chart.png");
        // expected product
        Product expectedProduct = new Product(
                null,
                "product1",
                "category1",
                1000,
                "logo.png",
                "chart.png");
        // mock findById
        given(categoryRepository
                .findById(categoryId))
                .willReturn(Optional.of(category));
        // mock save
        given(productRepository.save(expectedProduct)).willReturn(product);

        // when
        underTest.addProduct(addProductRequest, categoryId);

        // then
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());
        assertThat(productCaptor.getValue()).isEqualTo(expectedProduct);
    }

    @Test
    void checkIfAddProductThrowsException_whenInvalidCategoryId() throws NotFoundException {
        // given
        String categoryId = "category2";
        // mock findById
        given(categoryRepository.findById(categoryId)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest
                .addProduct(any(AddProductRequest.class), categoryId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Category not found");
    }

    @Test
    void checkIfEditsProduct_whenValidProductId() throws NotFoundException {
        // given
        String productId = "product1";
        // edit product request
        EditProductRequest editProductRequest = new EditProductRequest(
                "new-title",
                null,
                669,
                null,
                "new-chart.png");
        // expected updated product
        Product expectedProduct = new Product(
                "product1",
                "new-title",
                "category1",
                669,
                "logo.png",
                "new-chart.png");
        // mock findById
        given(productRepository
                .findById(productId))
                .willReturn(Optional.of(product));
        // mock save
        given(productRepository
                .save(expectedProduct))
                .willReturn(expectedProduct);

        // when
        underTest.editProduct(editProductRequest, productId);

        // then
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());
        assertThat(productCaptor.getValue()).isEqualTo(expectedProduct);
    }

    @Test
    void checkIfEditProductThrowsException_whenInvalidProductId() {
        // given
        String productId = "product2";
        // mock findById
        given(productRepository
                .findById(productId))
                .willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest
                .editProduct(any(EditProductRequest.class), productId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Product not found");
    }

    @Test
    void checkIfDeletesProduct_whenValidProductId() throws NotFoundException {
        // given
        String productId = "product1";
        // mock findById
        given(productRepository
                .findById(productId))
                .willReturn(Optional.of(product));

        // when
        underTest.deleteProduct(productId);

        // then
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).delete(productCaptor.capture());
        assertThat(productCaptor.getValue()).isEqualTo(product);
    }

    @Test
    void checkIfDeleteProductThrowsException_whenInvalidProductId() {
        // given
        String productId = "product2";
        // mock findById
        given(productRepository
                .findById(productId))
                .willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest
                .deleteProduct(productId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Product not found");
    }
}