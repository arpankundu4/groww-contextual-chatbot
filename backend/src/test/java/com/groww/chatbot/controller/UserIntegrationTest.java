package com.groww.chatbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groww.chatbot.dto.GrowwUserDetails;
import com.groww.chatbot.exception.AlreadyExistsException;
import com.groww.chatbot.exception.GrowwAuthenticationException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.LoginRequest;
import com.groww.chatbot.exchanges.RegistrationRequest;
import com.groww.chatbot.exchanges.UserDetailsResponse;
import com.groww.chatbot.model.User;
import com.groww.chatbot.service.UserServiceImpl;
import com.groww.chatbot.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.groww.chatbot.config.ApiRoutes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * integration tests
 * for user controller
 */

@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void whenValidLoginRequest_thenReturns200() throws Exception {
        // create login request
        LoginRequest loginRequest = new LoginRequest("test@groww.in", "password");
        // create user
        User user = new User();
        user.setId("test123");
        user.setName("Test");
        user.setEmail("test@groww.in");
        user.setPassword("password");
        user.setRole("USER");
        // create groww user details from user
        GrowwUserDetails growwUserDetails = new GrowwUserDetails(user);

        // mock authenticate
        Authentication authentication = mock(Authentication.class);
        authentication.setAuthenticated(true);
        when(authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())))
                .thenReturn(authentication);

        // mock loadUserByUsername
        when(userService
                .loadUserByUsername(loginRequest.getEmail()))
                .thenReturn(growwUserDetails);

        // mock generateToken
        when(jwtUtil
                .generateToken(growwUserDetails))
                .thenReturn(anyString());

        // mock post request
        mockMvc.perform(post(LOGIN_URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void whenInvalidLoginRequest_thenReturns400() throws Exception {
        // create invalid login request
        LoginRequest loginRequest = new LoginRequest("test", " ");
        // mock post request
        mockMvc.perform(post(LOGIN_URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidLoginCredentials_thenReturns401() throws Exception {
        // create login request
        LoginRequest loginRequest = new LoginRequest("test@groww.in", "password");

        // mock authenticate
        // for invalid login credentials
        Mockito.when(authenticationManager
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new GrowwAuthenticationException("Invalid email or password"));

        // mock post request
        mockMvc.perform(post(LOGIN_URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());

        // verify no method invoked after this
        verify(userService, never()).loadUserByUsername(any());
        verify(jwtUtil, never()).generateToken(any());
    }

    @Test
    void whenValidRegistrationRequest_thenReturns201() throws Exception {
        // create valid registration request
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setName("Test");
        registrationRequest.setEmail("test@groww.in");
        registrationRequest.setPassword("password");

        // mock createUser
        doNothing().when(userService).createUser(registrationRequest);

        // mock post request
        mockMvc.perform(post(REGISTER_URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenInvalidRegistrationRequest_thenReturns400() throws Exception {
        // create invalid registration request
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmail("test");
        registrationRequest.setPassword("");

        // mock post request
        mockMvc.perform(post(REGISTER_URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEmailAlreadyExists_thenReturns409() throws Exception {
        // create registration request
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setName("Test");
        registrationRequest.setEmail("test@groww.in");
        registrationRequest.setPassword("password");

        // mock createUser to throw exception
        doThrow(new AlreadyExistsException("User already exists with email "
                + registrationRequest.getEmail()))
                .when(userService)
                .createUser(registrationRequest);

        // mock post request
        mockMvc.perform(post(REGISTER_URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "test@groww.in")
    void whenUserExists_thenReturns200() throws Exception {
        // mock extractEmailFromAuthHeader
        when(jwtUtil
                .extractEmailFromAuthHeader(anyString()))
                .thenReturn("test@groww.in");

        // mock getUser
        when(userService
                .getUser("test@groww.in"))
                .thenReturn(new UserDetailsResponse());

        // mock get request
        mockMvc.perform(get(GET_USER_URL)
                .header("Authorization", anyString()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@groww.in")
    void whenUserDoesNotExist_thenReturns404() throws Exception {
        // mock extractEmailFromAuthHeader
        when(jwtUtil
                .extractEmailFromAuthHeader(anyString()))
                .thenReturn("test@groww.in");

        // mock getUser
        // to throw exception
        when(userService
                .getUser("test@groww.in"))
                .thenThrow(new NotFoundException("User not found"));

        // mock get request
        mockMvc.perform(get(GET_USER_URL)
                .header("Authorization", anyString()))
                .andExpect(status().isNotFound());
    }

}