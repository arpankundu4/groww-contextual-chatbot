package com.groww.chatbot.exchanges;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * exchanges class
 * for add category/subcategory request
 */

@Data
public class AddCategoryRequest {

    @NotBlank
    private String title;

    @NotNull
    private boolean hidden;

}