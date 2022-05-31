package com.groww.chatbot.service;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.model.Order;
import com.groww.chatbot.model.User;
import com.groww.chatbot.repository.OrderRepository;
import com.groww.chatbot.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class implementation for order
 * overrides methods of interface
 */

@Log4j2
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getOrders(String email) throws NotFoundException {
        return userRepository
                .findByEmail(email)
                .map(user -> orderRepository
                .findAllById(user.getOrderIds()))
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
