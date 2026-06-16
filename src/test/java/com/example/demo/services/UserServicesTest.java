package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServicesTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServices userServices;

    @Test
    void getAllUserReturnsUsersFromRepository() {
        User user1 = new User();
        User user2 = new User();
        List<User> expected = Arrays.asList(user1, user2);
        doReturn(expected).when(userRepository).findAll();

        List<User> actual = userServices.getAllUser();

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertSame(user1, actual.get(0));
        assertSame(user2, actual.get(1));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserReturnsUserFromOptional() {
        User expected = new User();
        doReturn(Optional.of(expected)).when(userRepository).findById(7);

        User actual = userServices.getUser(7);

        assertNotNull(actual);
        assertSame(expected, actual);
        verify(userRepository, times(1)).findById(7);
    }

    @Test
    void getUserByEmailReturnsUserFromRepository() {
        User expected = new User();
        doReturn(expected).when(userRepository).findUserByUemail("test@example.com");

        User actual = userServices.getUserByEmail("test@example.com");

        assertNotNull(actual);
        assertSame(expected, actual);
        verify(userRepository, times(1)).findUserByUemail("test@example.com");
    }

    @Test
    void updateUserSetsIdAndSavesUser() {
        User user = new User();

        userServices.updateUser(user, 42);

        assertEquals(42, user.getU_id());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUserDelegatesToRepository() {
        userServices.deleteUser(11);

        assertTrue(true);
        verify(userRepository, times(1)).deleteById(11);
    }

    @Test
    void addUserDelegatesToRepositorySave() {
        User user = new User();

        userServices.addUser(user);

        assertNotNull(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void validateLoginCredentialsReturnsTrueWhenMatchingUserExists() {
        User matchingUser = new User();
        matchingUser.setUemail("john@example.com");
        matchingUser.setUpassword("secret");
        doReturn(Collections.singletonList(matchingUser)).when(userRepository).findAll();

        boolean result = userServices.validateLoginCredentials("john@example.com", "secret");

        assertTrue(result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void validateLoginCredentialsReturnsFalseWhenNoMatchingUserExists() {
        User user = new User();
        user.setUemail("john@example.com");
        user.setUpassword("secret");
        doReturn(Collections.singletonList(user)).when(userRepository).findAll();

        boolean result = userServices.validateLoginCredentials("jane@example.com", "wrong");

        assertFalse(result);
        verify(userRepository, times(1)).findAll();
    }
}