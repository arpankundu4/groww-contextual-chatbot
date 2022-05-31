package com.groww.chatbot.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * exception class
 * for invalid authentication object
 */
public class GrowwAuthenticationException extends AuthenticationException {

    /**
     * initiates new groww authentication exception
     * @param message exception message
     */
    public GrowwAuthenticationException(String message) {
        super(message);
    }

}
