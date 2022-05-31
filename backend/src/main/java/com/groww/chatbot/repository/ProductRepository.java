package com.groww.chatbot.repository;

import com.groww.chatbot.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * repository class for products data
 */
public interface ProductRepository extends MongoRepository<Product, String> {

    // override findAllById method
    // to return list instead of iterable
    @NonNull
    List<Product> findAllById(@NonNull Iterable<String> ids);

}
