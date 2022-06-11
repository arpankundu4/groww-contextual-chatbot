package com.groww.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * model class for orders data
 * gets persisted in database
 */

@Data
@Document("orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;

    @NotNull
    private Product product;

    @Indexed
    @NotBlank
    private String userId;

    @NotBlank
    private String date;

    @NotNull
    @Positive
    private int quantity;

    @NotNull
    @Positive
    private double orderValue;

    @NotBlank
    private String status;

}
