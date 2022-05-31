package com.groww.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * model class for product categories data
 * gets persisted in database
 */

@Data
@AllArgsConstructor
@Document("product-categories")
public class ProductCategory {

    @Id
    private String id;

    @NotBlank
    private String title;

    private List<String> productIds;

}
