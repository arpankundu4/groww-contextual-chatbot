package com.groww.chatbot.repository;

import com.groww.chatbot.model.Order;
import com.groww.chatbot.model.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * unit tests
 * for order repository
 */

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository underTest;

    private Order order;

    private List<Order> orders;

    private List<String> orderIds;

    @BeforeAll
    void beforeAll() {
        // create order
        order = new Order("order1",
                               new Product(),
                      "user1",
                        "25/05/2022",
                     10,
                   10000,
                      "Processing");

        // create orders list
        orders = List.of(order);

        // create order ids list
        orderIds = List.of(order.getId());
    }

    @Test
    void checkIfReturnsOrdersList_whenAllOrdersFoundById() {
        // given
        underTest.saveAll(orders);
        List<Order> expectedOrders = orders;

        // when
        List<Order> resultOrders = underTest.findAllById(orderIds);

        // then
        assertThat(resultOrders).isEqualTo(expectedOrders);
    }

    @Test
    void checkIfReturnsEmptyList_whenNoOrdersFoundById() {
        // given
        List<Order> expectedOrders = Collections.emptyList();

        // when
        List<Order> resultOrders = underTest.findAllById(Collections.emptyList());

        // then
        assertThat(resultOrders).isEqualTo(expectedOrders);
    }

    @AfterAll
    void afterAll() {
        underTest.deleteAll(orders);
    }
}