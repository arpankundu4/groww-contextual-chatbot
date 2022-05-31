package com.groww.chatbot.repository;

import com.groww.chatbot.model.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * repository class for product categories data
 */
public interface ProductCategoryRepository extends MongoRepository<ProductCategory, String> {

}
