package com.groww.chatbot.exception;

/**
 * exception class
 * for all kinds of already exists exceptions
 */
public class AlreadyExistsException extends Exception {

    /**
     * initiates a new already exists exception
     * @param message exception message
     */
    public AlreadyExistsException(String message) {
        super(message);
    }

}
