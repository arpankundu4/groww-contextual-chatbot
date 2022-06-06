package com.groww.chatbot.repository;

import com.groww.chatbot.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * repository class for orders data
 */
public interface OrderRepository extends MongoRepository<Order, String> {

    // finds orders by user id
    List<Order> findAllByUserId(String userId);

}
