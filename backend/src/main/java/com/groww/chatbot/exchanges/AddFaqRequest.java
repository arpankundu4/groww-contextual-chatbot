package com.groww.chatbot.exchanges;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * exchanges class
 * for add faq request
 */

@Data
public class AddFaqRequest {

    @NotBlank
    private String question;

    @NotBlank
    private String answer;

    @NotNull
    private boolean hidden;

}
