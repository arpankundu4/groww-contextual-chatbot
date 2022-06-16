package com.groww.chatbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller class
 * for backend root url
 */

@RestController
public class RootController {
    @GetMapping("/")
    public String getHome() {
        final String welcomeMessage =
                "Welcome to Groww Contextual Chatbot backend! " +
                "Check out OpenAPI docs at " +
                "https://groww-chatbot.herokuapp.com/swagger-ui/";
        return welcomeMessage;
    }
}