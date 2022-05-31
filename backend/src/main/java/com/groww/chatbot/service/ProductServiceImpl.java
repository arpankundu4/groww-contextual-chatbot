package com.groww.chatbot.service;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.model.Product;
import com.groww.chatbot.model.ProductCategory;
import com.groww.chatbot.repository.ProductCategoryRepository;
import com.groww.chatbot.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class implementation for product
 * overrides methods of interface
 */

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<Product> getProducts(String categoryId) throws NotFoundException {
        return productCategoryRepository
                .findById(categoryId)
                .map(productCategory -> productRepository
                .findAllById(productCategory.getProductIds()))
                .orElseThrow(() -> new NotFoundException("Product category not found"));
    }

    @Override
    public Product getProduct(String productId) throws NotFoundException {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

}
