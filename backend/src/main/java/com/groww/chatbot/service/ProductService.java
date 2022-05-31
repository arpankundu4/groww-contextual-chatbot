package com.groww.chatbot.service;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class interface for product
 * shows available methods
 */
public interface ProductService {

    /**
     * gets list of products
     * for the specified product category
     *
     * @param categoryId category id
     * @return list of products
     * @throws NotFoundException if product category not found
     */
    public List<Product> getProducts(String categoryId) throws NotFoundException;

    /**
     * gets a product
     * for the specified product id
     *
     * @param productId product id
     * @return a product
     * @throws NotFoundException if product not found
     */
    public Product getProduct(String productId) throws NotFoundException;

}
