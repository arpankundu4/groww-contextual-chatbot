package com.groww.chatbot.exchanges;

import lombok.Data;

/**
 * exchanges class
 * for edit faq request
 */

@Data
public class EditFaqRequest {

    private String parentId;
    private String question;
    private String answer;
    private boolean hidden;

}
