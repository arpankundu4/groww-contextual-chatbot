package com.groww.chatbot.service;

import com.groww.chatbot.dto.GrowwUserDetails;
import com.groww.chatbot.exception.AlreadyExistsException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.RegistrationRequest;
import com.groww.chatbot.exchanges.UserDetailsResponse;
import com.groww.chatbot.model.User;
import com.groww.chatbot.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * service class implementation for user
 * implements UserService & UserDetailsService
 * overriding their methods
 */

@Log4j2
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final ModelMapper mapper = new ModelMapper();

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    // method to load user details
    // by username (email in our case)
    public GrowwUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(GrowwUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email"));
    }

    @Override
    public void createUser(RegistrationRequest registrationRequest) throws AlreadyExistsException {
        String email = registrationRequest.getEmail();
        // check if user already exists with email
        if(userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException("User already exists with email " + email);
        }
        // create & persist user
        User user = mapper.map(registrationRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
    }

    @Override
    public UserDetailsResponse getUser(String email) throws NotFoundException {
        return userRepository
                .findByEmail(email)
                .map(user -> mapper.map(user, UserDetailsResponse.class))
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

}
