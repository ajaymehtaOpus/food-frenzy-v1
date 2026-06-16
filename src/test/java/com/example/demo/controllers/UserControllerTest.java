package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.services.UserServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServices services;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void addUserShouldDelegateToServiceAndReturnRedirect() {
        String viewName = userController.addUser(user);

        assertEquals("redirect:/admin/services", viewName);
        verify(services, times(1)).addUser(user);
    }

    @Test
    void updateUserShouldDelegateToServiceAndReturnRedirect() {
        int id = 42;

        String viewName = userController.updateUser(user, id);

        assertEquals("redirect:/admin/services", viewName);
        verify(services, times(1)).updateUser(user, id);
    }

    @Test
    void deleteUserShouldDelegateToServiceAndReturnRedirect() {
        int id = 7;

        String viewName = userController.deleteUser(id);

        assertEquals("redirect:/admin/services", viewName);
        verify(services, times(1)).deleteUser(id);
    }
}