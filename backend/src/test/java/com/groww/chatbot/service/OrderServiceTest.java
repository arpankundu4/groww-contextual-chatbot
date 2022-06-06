package com.groww.chatbot.service;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.model.User;
import com.groww.chatbot.repository.OrderRepository;
import com.groww.chatbot.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * unit tests
 * for order service
 */

@DataMongoTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl underTest;

    private User user;

    @BeforeAll
    void beforeAll() {
        // test user
        user = new User();
        user.setId("user1");
    }

    @Test
    void checkIfGetsOrders_whenValidEmail() throws NotFoundException {
        // given
        String email = "user@groww.in";
        // mock findByEmail
        when(userRepository
                .findByEmail(email))
                .thenReturn(Optional.of(user));

        // when
        underTest.getOrders(email);

        // then
        // capture user id
        ArgumentCaptor<String> userIdCaptor = ArgumentCaptor.forClass(String.class);
        verify(orderRepository).findAllByUserId(userIdCaptor.capture());
        // compare with actual user id
        assertThat(userIdCaptor.getValue()).isEqualTo(user.getId());
    }

    @Test
    void checkIfThrowsException_whenInvalidEmail() {
        // given
        String email = "invalid@groww.in";
        // mock findByEmail to throw exception
        when(userRepository
                .findByEmail(email))
                .thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest
                .getOrders(email))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");

        // verify findAllByUserId is not invoked after this
        verify(orderRepository, never()).findAllByUserId(any());
    }

}