package com.groww.chatbot.exchanges;

import lombok.Data;

/**
 * exchanges class
 * for FAQs response
 */

@Data
public class FaqResponse {

    private String id;
    private String question;
    private String answer;

}
