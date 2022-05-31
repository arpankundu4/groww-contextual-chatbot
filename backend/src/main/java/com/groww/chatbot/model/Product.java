package com.groww.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * model class for products data
 * gets persisted in database
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("products")
public class Product {

    @Id
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String categoryId;

    @NotNull
    @Positive
    private float price;

    @NotBlank
    private String logoUrl;

    @NotBlank
    private String chartUrl;

}
