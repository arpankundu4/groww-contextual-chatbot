package com.groww.chatbot.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * exchanges class
 * for edit product request
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditProductRequest {

    private String title;
    private String categoryId;
    private float price;
    private String logoUrl;
    private String chartUrl;

}
