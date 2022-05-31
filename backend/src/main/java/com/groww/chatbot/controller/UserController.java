package com.groww.chatbot.controller;

import com.groww.chatbot.dto.GrowwUserDetails;
import com.groww.chatbot.exception.AlreadyExistsException;
import com.groww.chatbot.exception.GrowwAuthenticationException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.LoginRequest;
import com.groww.chatbot.exchanges.LoginResponse;
import com.groww.chatbot.exchanges.RegistrationRequest;
import com.groww.chatbot.service.UserServiceImpl;
import com.groww.chatbot.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.groww.chatbot.config.ApiRoutes.*;

/**
 * controller class
 * for user related APIs
 *
 * refer to ApiRoutes in com.groww.chatbot.config
 * for all API URLs
 */

@Log4j2
@RestController
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(LOGIN_URL)
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("Login user request");
        try {
            // authenticate credentials
            log.info("Authenticating credentials");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                            loginRequest.getPassword()));
        } catch (GrowwAuthenticationException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
        // get user details
        final GrowwUserDetails growwUserDetails = userService.loadUserByUsername(loginRequest.getEmail());
        // generate token
        log.info("Generating token");
        final String token = jwtUtil.generateToken(growwUserDetails);

        // return login response
        log.info("Login successful");
        return ResponseEntity.ok(new LoginResponse(token,
                                                   growwUserDetails.getUserId(),
                                                   growwUserDetails.getName()));
    }

    // TODO: after registration success, prompt user to login or do that automatically?

    @PostMapping(REGISTER_URL)
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        log.info("Register user request");
        try {
            userService.createUser(registrationRequest);
            log.info("User registered successfully");
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);

        } catch (AlreadyExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping(GET_USER_URL)
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authorizationHeader) {
        log.info("Get user account details request");
        String email = jwtUtil.extractEmailFromAuthHeader(authorizationHeader);
        try {
            return ResponseEntity.ok(userService.getUser(email));
        } catch (NotFoundException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
