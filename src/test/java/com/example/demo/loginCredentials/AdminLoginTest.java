package com.example.demo.loginCredentials;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AdminLoginTest {

    @Test
    void shouldSetAndGetEmailAndPassword() {
        AdminLogin adminLogin = new AdminLogin();

        adminLogin.setEmail("admin@example.com");
        adminLogin.setPassword("secret");

        assertEquals("admin@example.com", adminLogin.getEmail());
        assertEquals("secret", adminLogin.getPassword());
    }

    @Test
    void shouldReturnFormattedToString() {
        AdminLogin adminLogin = new AdminLogin();
        adminLogin.setEmail("admin@example.com");
        adminLogin.setPassword("secret");

        String result = adminLogin.toString();

        assertNotNull(result);
        assertEquals("AdminLogin [name=admin@example.com, password=secret]", result);
    }
}