package com.groww.chatbot.exception;

/**
 * exception class
 * for all kinds of access denied exceptions
 */
public class AccessDeniedException extends Exception {

    /**
     * initiates new access denied exception
     * @param message exception message
     */
    public AccessDeniedException(String message) {
        super(message);
    }

}
