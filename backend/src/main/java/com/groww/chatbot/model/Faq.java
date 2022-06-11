package com.groww.chatbot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * model class for FAQs data
 * gets persisted in database
 */

@Data
@Document("faqs")
public class Faq {

    @Id
    private String id;

    @Indexed
    @NotBlank
    private String parentId;

    @NotBlank
    private String question;

    @NotBlank
    private String answer;

    @NotNull
    private boolean hidden;

}
