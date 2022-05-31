package com.groww.chatbot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * model class for FAQ categories data
 * gets persisted in database
 */

@Data
@Document("faq-categories")
public class FaqCategory {

    @Id
    private String id;

    private String parentId;    // null for a category

    @NotBlank
    private String title;

    @NotNull
    private boolean isPrivate;

}
