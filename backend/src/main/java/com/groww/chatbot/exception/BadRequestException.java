package com.groww.chatbot.exception;

/**
 * exception class
 * for all kinds of bad request exceptions
 */
public class BadRequestException extends Exception {

    /**
     * initiates a new bad request exception
     * @param message exception message
     */
    public BadRequestException(String message) {
        super(message);
    }

}
