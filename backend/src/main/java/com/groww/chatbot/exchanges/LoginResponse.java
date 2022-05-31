package com.groww.chatbot.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * exchanges class
 * for outgoing login response
 */

@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String userId;
    private String name;

}
