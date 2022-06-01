package com.groww.chatbot.service;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.PlaceOrderRequest;
import com.groww.chatbot.exchanges.PlaceOrderResponse;
import com.groww.chatbot.model.Order;
import com.groww.chatbot.model.Product;
import com.groww.chatbot.model.User;
import com.groww.chatbot.repository.OrderRepository;
import com.groww.chatbot.repository.ProductRepository;
import com.groww.chatbot.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Autowired
    private ProductRepository productRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<Order> getOrders(String email) throws NotFoundException {
        return userRepository
                .findByEmail(email)
                .map(user -> orderRepository
                .findAllById(user.getOrderIds()))
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest, String email) throws NotFoundException {
        // get product
        Product product = productRepository
                .findById(placeOrderRequest.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));
        // get user
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // create order
        Order order = mapper.map(placeOrderRequest, Order.class);
        order.setProduct(product);
        order.setUserId(user.getId());
        order.setDate(LocalDate.now().toString());
        order.setOrderValue(product.getPrice() * order.getQuantity());
        order.setStatus("Processing");
        order = orderRepository.save(order);

        // add order reference to user
        userRepository.save(user.addOrderId(order.getId()));

        return mapper.map(order, PlaceOrderResponse.class);
    }
}
