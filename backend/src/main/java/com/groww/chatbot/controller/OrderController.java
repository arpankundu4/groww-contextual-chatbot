package com.groww.chatbot.controller;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.service.OrderService;
import com.groww.chatbot.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static com.groww.chatbot.config.ApiRoutes.*;

/**
 * controller class
 * for order related APIs
 *
 * refer to ApiRoutes in com.groww.chatbot.config
 * for all API URLs
 */

@Log4j2
@RestController
public class OrderController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OrderService orderService;

    @GetMapping(GET_ORDERS_URL)
    public ResponseEntity<?> getOrders(@RequestHeader("Authorization") String authorizationHeader) {
        log.info("Get orders request");
        String email = jwtUtil.extractEmailFromAuthHeader(authorizationHeader);
        try {
            return ResponseEntity.ok(orderService.getOrders(email));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
