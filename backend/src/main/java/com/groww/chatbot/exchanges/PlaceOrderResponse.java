package com.groww.chatbot.exchanges;

import com.groww.chatbot.model.Product;
import lombok.Data;

/**
 * exchanges class
 * for place order response
 */

@Data
public class PlaceOrderResponse {

    private String id;

    private Product product;

    private String date;

    private double orderValue;

    private String status;

}
