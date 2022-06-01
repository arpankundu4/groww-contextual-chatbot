package com.groww.chatbot.exchanges;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * exchanges class
 * for place order request
 */

@Data
public class PlaceOrderRequest {

    @NotBlank
    private String productId;

    @NotNull
    @Positive
    private int quantity;
}
