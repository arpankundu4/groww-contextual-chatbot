package com.groww.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * model class for FAQ categories data
 * gets persisted in database
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("categories")
public class Category {

    @Id
    private String id;

    @Indexed
    private String parentId;    // null for a category

    @NotBlank
    private String title;

    @NotNull
    private boolean hidden;

}
