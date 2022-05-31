package com.groww.chatbot.repository;

import com.groww.chatbot.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * repository class for orders data
 */
public interface OrderRepository extends MongoRepository<Order, String> {

    // override findAllById method
    // to return list instead of iterable
    List<Order> findAllById(Iterable<String> ids);

}
