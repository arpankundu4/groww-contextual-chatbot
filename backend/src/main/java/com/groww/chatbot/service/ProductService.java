package com.groww.chatbot.service;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.AddProductRequest;
import com.groww.chatbot.exchanges.EditProductRequest;
import com.groww.chatbot.model.Product;

import java.util.List;

/**
 * Service class interface for product
 * shows available methods
 */
public interface ProductService {

    /**
     * gets list of products
     * for the specified category
     *
     * @param categoryId category id
     * @return list of products
     * @throws NotFoundException if category not found
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

    /**
     * adds a product
     *
     * @param addProductRequest add product request body
     * @param categoryId category id
     * @return newly created product
     * @throws NotFoundException if category not found
     */
    public Product addProduct(AddProductRequest addProductRequest, String categoryId) throws NotFoundException;

    /**
     * edits a product
     *
     * @param editProductRequest edit product request body
     * @param productId product id
     * @return updated product
     * @throws NotFoundException if product not found
     */
    public Product editProduct(EditProductRequest editProductRequest, String productId) throws NotFoundException;

    /**
     * deletes a product
     *
     * @param productId product id
     * @throws NotFoundException if product not found
     */
    public void deleteProduct(String productId) throws NotFoundException;

}
