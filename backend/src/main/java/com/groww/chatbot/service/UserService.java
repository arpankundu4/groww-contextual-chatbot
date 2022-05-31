package com.groww.chatbot.service;

import com.groww.chatbot.exception.AlreadyExistsException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.RegistrationRequest;
import com.groww.chatbot.exchanges.UserDetailsResponse;

/**
 * service class interface for user
 */
public interface UserService {

    /**
     * creates a new user
     *
     * @param registrationRequest registration request body
     * @throws AlreadyExistsException if email already exists
     */
    public void createUser(RegistrationRequest registrationRequest) throws AlreadyExistsException;

    /**
     * gets user account details
     *
     * @param email user's email
     * @return user account details
     * @throws NotFoundException if user not found
     */
    public UserDetailsResponse getUser(String email) throws NotFoundException;

}
