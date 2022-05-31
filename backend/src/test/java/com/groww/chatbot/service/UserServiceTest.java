package com.groww.chatbot.service;

import com.groww.chatbot.dto.GrowwUserDetails;
import com.groww.chatbot.exception.AlreadyExistsException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.RegistrationRequest;
import com.groww.chatbot.exchanges.UserDetailsResponse;
import com.groww.chatbot.model.User;
import com.groww.chatbot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * unit tests
 * for user service
 */

@DataMongoTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl underTest;

    @Test
    void checkIfCanCreateUser() throws AlreadyExistsException {
        // given
        // create test registration request
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setName("Test");
        registrationRequest.setEmail("test@groww.in");
        registrationRequest.setPassword("password");
        // expected user
        User user = new User();
        user.setName("Test");
        user.setEmail("test@groww.in");
        user.setPassword("password");
        user.setRole("USER");

        // when
        underTest.createUser(registrationRequest);

        // then
        // get captured user
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        // compare with expected user
        // ignore password field as it gets encrypted
        assertThat(capturedUser)
                .usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(user);
    }

    @Test
    void checkIfCannotCreateUser() {
        // given
        // create test registration request
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setName("Test");
        registrationRequest.setEmail("test@groww.in");
        registrationRequest.setPassword("password");
        // mock existsByEmail to return true
        given(userRepository.existsByEmail(registrationRequest.getEmail())).willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest
                .createUser(registrationRequest))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessage("User already exists with email " + registrationRequest.getEmail());
        // verify that user is not saved after this
        verify(userRepository, never()).save(any());
    }

    @Test
    void checkIfCanGetUser() throws NotFoundException {
        // given
        // create user
        String email = "test@groww.in";
        User user = new User();
        user.setName("Test");
        user.setEmail(email);
        user.setPassword("password");
        user.setRole("USER");
        // mock findByEmail to return this user
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        // expected user details response
        UserDetailsResponse expectedResponse = new UserDetailsResponse();
        expectedResponse.setName("Test");
        expectedResponse.setEmail(email);

        // when
        UserDetailsResponse resultResponse = underTest.getUser(email);

        // then
        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @Test
    void checkIfCannotGetUser() {
        // given
        String email = "test@groww.in";
        // mock findByEmail to return empty response
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest
                .getUser(email))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void checkIfCanLoadUserByUsername() {
        // given
        // create user
        String email = "test@groww.in";
        User user = new User();
        user.setId("test123");
        user.setName("Test");
        user.setEmail(email);
        user.setPassword("password");
        user.setRole("USER");
        // mock findByEmail to return this user
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        // expected groww user details
        GrowwUserDetails expectedDetails = new GrowwUserDetails(user);

        // when
        GrowwUserDetails resultDetails = underTest.loadUserByUsername(email);

        // then
        assertThat(resultDetails)
                .usingRecursiveComparison()
                .isEqualTo(expectedDetails);
    }

    @Test
    void checkIfCannotLoadUserByUsername() {
        // given
        String email = "test@groww.in";
        // mock findByEmail to return empty response
        given(userRepository.findByEmail("test@groww.in")).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest
                .loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("No user found with email");
    }

}