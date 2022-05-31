package com.groww.chatbot.exception;

/**
 * exception class
 * for all kinds of not found exceptions
 */
public class NotFoundException extends  Exception {

    /**
     * initiates a new not found exception
     * @param message exception message
     */
    public NotFoundException(String message) {
        super(message);
    }

}