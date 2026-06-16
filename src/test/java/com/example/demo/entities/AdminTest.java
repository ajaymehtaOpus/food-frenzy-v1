package com.example.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AdminTest {

    @Test
    void shouldSetAndGetAllFields() {
        Admin admin = new Admin();

        admin.setAdminId(42);
        admin.setAdminName("Alice Admin");
        admin.setAdminEmail("alice@example.com");
        admin.setAdminPassword("secret");
        admin.setAdminNumber("1234567890");

        assertEquals(42, admin.getAdminId());
        assertEquals("Alice Admin", admin.getAdminName());
        assertEquals("alice@example.com", admin.getAdminEmail());
        assertEquals("secret", admin.getAdminPassword());
        assertEquals("1234567890", admin.getAdminNumber());
    }

    @Test
    void shouldFormatToStringWithAllFields() {
        Admin admin = new Admin();
        admin.setAdminId(7);
        admin.setAdminName("Bob");
        admin.setAdminEmail("bob@example.com");
        admin.setAdminPassword("pwd123");
        admin.setAdminNumber("555-0100");

        String expected = "Admin [adminId=7, adminName=Bob, adminEmail=bob@example.com, adminPassword=pwd123, adminNumber=555-0100]";

        assertEquals(expected, admin.toString());
    }

    @Test
    void shouldCreateAdminInstance() {
        Admin admin = new Admin();

        assertNotNull(admin);
    }
}