package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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

        assertEquals(expected, actual);
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getUserReturnsUserFromOptional() {
        User expected = new User();
        doReturn(Optional.of(expected)).when(userRepository).findById(10);

        User actual = userServices.getUser(10);

        assertEquals(expected, actual);
        verify(userRepository, times(1)).findById(10);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getUserByEmailReturnsUserFromRepository() {
        User expected = new User();
        doReturn(expected).when(userRepository).findUserByUemail(eq("test@example.com"));

        User actual = userServices.getUserByEmail("test@example.com");

        assertEquals(expected, actual);
        verify(userRepository, times(1)).findUserByUemail("test@example.com");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUserSetsIdAndSavesUser() {
        User user = new User();

        userServices.updateUser(user, 42);

        assertEquals(42, user.getU_id());
        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteUserDelegatesToRepository() {
        userServices.deleteUser(7);

        assertTrue(true);
        verify(userRepository, times(1)).deleteById(7);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void addUserDelegatesToRepository() {
        User user = new User();

        userServices.addUser(user);

        assertNotNull(user);
        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
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
        verifyNoMoreInteractions(userRepository);
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
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void validateLoginCredentialsReturnsFalseWhenRepositoryReturnsEmptyList() {
        doReturn(Collections.emptyList()).when(userRepository).findAll();

        boolean result = userServices.validateLoginCredentials("any@example.com", "any");

        assertFalse(result);
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }
}