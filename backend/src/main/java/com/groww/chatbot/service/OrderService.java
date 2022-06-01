package com.groww.chatbot.service;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.PlaceOrderRequest;
import com.groww.chatbot.exchanges.PlaceOrderResponse;
import com.groww.chatbot.model.Order;

import java.util.List;

/**
 * Service class interface for order
 * shows available methods
 */
public interface OrderService {

    /**
     * gets list of orders
     * for the user associated with specified email
     *
     * @param email user's email
     * @return list of orders
     * @throws NotFoundException if user not found
     */
    public List<Order> getOrders(String email) throws NotFoundException;

    /**
     * creates a new order
     *
     * @param placeOrderRequest place order request body
     * @param email user's email
     * @return place order response
     * @throws NotFoundException if product or user not found
     */
    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest, String email) throws NotFoundException;

}
