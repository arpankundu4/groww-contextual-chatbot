package com.groww.chatbot.exchanges;

import lombok.Data;

/**
 * exchanges class
 * for edit category/subcategory request
 */

@Data
public class EditCategoryRequest {

    private String parentId;
    private String title;
    private boolean hidden;

}
