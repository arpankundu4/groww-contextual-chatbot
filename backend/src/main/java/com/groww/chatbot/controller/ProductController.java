package com.groww.chatbot.controller;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.groww.chatbot.config.ApiRoutes.*;

/**
 * controller class
 * for product related APIs
 *
 * refer to ApiRoutes in com.groww.chatbot.config
 * for all API URLs
 */

@Log4j2
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(GET_PRODUCTS_URL)
    public ResponseEntity<?> getProducts(@PathVariable("categoryId") String categoryId) {
        log.info("Get products request");
        try {
            return ResponseEntity.ok(productService.getProducts(categoryId));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(GET_PRODUCT_URL)
    public ResponseEntity<?> getProduct(@PathVariable("productId") String productId) {
        log.info("Get a product request");
        try {
            return ResponseEntity.ok(productService.getProduct(productId));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
