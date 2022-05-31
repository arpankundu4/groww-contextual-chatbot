package com.groww.chatbot.repository;

import com.groww.chatbot.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * unit tests
 * for user repository
 */

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    private User expectedUser;

    @BeforeEach
    void setUp() {
        // Creating test user
        String email = "user@groww.in";
        expectedUser = new User();
        expectedUser.setName("Test");
        expectedUser.setEmail(email);
        expectedUser.setPassword("password");
        expectedUser.setRole("USER");
        underTest.save(expectedUser);
    }

    @Test
    void checkIfUserFoundByEmail() {
        // given
        String email = "user@groww.in";
        // when
        User resultUser = underTest.findByEmail(email).get();
        // then
        assertThat(resultUser).isEqualTo(expectedUser);
    }

    @Test
    void checkIfUserNotFoundByEmail() {
        // given
        String email = "user@gmail.com";
        // when
        Optional<User> resultUser = underTest.findByEmail(email);
        // then
        assertThat(resultUser).isEmpty();
    }

    @Test
    void checkIfUserExistsByEmail() {
        // given
        String email = "user@groww.in";
        // when
        boolean result = underTest.existsByEmail(email);
        // then
        assertThat(result).isTrue();
    }

    @Test
    void checkIfUserDoesNotExistByEmail() {
        // given
        String email = "user@gmail.com";
        // when
        boolean result = underTest.existsByEmail(email);
        // then
        assertThat(result).isFalse();
    }

    @AfterEach
    void tearDown() {
        // Deleting test user
        underTest.delete(expectedUser);
    }
}