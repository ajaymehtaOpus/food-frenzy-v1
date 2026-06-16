package com.example.demo.loginCredentials;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class UserLoginTest {

    @Test
    void shouldSetAndGetUserEmailAndPassword() {
        UserLogin userLogin = new UserLogin();

        userLogin.setUserEmail("test@example.com");
        userLogin.setUserPassword("secret");

        assertEquals("test@example.com", userLogin.getUserEmail());
        assertEquals("secret", userLogin.getUserPassword());
    }

    @Test
    void shouldReturnFormattedToString() {
        UserLogin userLogin = new UserLogin();
        userLogin.setUserEmail("test@example.com");
        userLogin.setUserPassword("secret");

        assertEquals("UserLogin [userEmail=test@example.com, userPassword=secret]", userLogin.toString());
    }

    @Test
    void shouldInstantiateUserLogin() {
        UserLogin userLogin = new UserLogin();

        assertNotNull(userLogin);
    }
}