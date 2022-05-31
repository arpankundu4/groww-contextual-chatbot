package com.groww.chatbot.exchanges;

import lombok.Data;

/**
 * exchanges class
 * for logged-in user's context
 */

@Data
public class Context {

    private String userId;
    private String orderId;
    private String productId;

}
