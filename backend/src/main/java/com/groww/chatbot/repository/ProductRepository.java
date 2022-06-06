package com.groww.chatbot.repository;

import com.groww.chatbot.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * repository class for products data
 */
public interface ProductRepository extends MongoRepository<Product, String> {

    // finds products by category id
    List<Product> findAllByCategoryId(String categoryId);

    void deleteAllByCategoryId(String categoryId);

}
