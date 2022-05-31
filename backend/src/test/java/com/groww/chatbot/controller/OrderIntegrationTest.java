package com.groww.chatbot.controller;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.service.OrderService;
import com.groww.chatbot.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.groww.chatbot.config.ApiRoutes.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * integration tests
 * for order controller
 */

@SpringBootTest
@AutoConfigureMockMvc
class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser(username = "test@groww.in")
    void whenUserExists_thenReturns200() throws Exception {
        // mock extractEmailFromAuthHeader
        when(jwtUtil
                .extractEmailFromAuthHeader(anyString()))
                .thenReturn("test@groww.in");

        // mock getOrders
        when(orderService
                .getOrders("test@groww.in"))
                .thenReturn(Collections.emptyList());

        // mock get request
        mockMvc.perform(get(GET_ORDERS_URL)
                .header("Authorization", anyString()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@groww.in")
    void whenUserDoesNotExist_thenReturns404() throws Exception {
        // mock extractEmailFromAuthHeader
        when(jwtUtil
                .extractEmailFromAuthHeader(anyString()))
                .thenReturn("test@groww.in");

        // mock getOrders to throw exception
        when(orderService
                .getOrders("test@groww.in"))
                .thenThrow(new NotFoundException("User not found"));

        // mock get request
        mockMvc.perform(get(GET_ORDERS_URL)
                .header("Authorization", anyString()))
                .andExpect(status().isNotFound());
    }
}