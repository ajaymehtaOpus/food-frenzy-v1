package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Admin;
import com.example.demo.repositories.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminServicesTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServices adminServices;

    @Test
    void getAllShouldReturnAdminsFromRepository() {
        Admin admin1 = new Admin();
        Admin admin2 = new Admin();
        List<Admin> expected = Arrays.asList(admin1, admin2);

        when(adminRepository.findAll()).thenReturn(expected);

        List<Admin> actual = adminServices.getAll();

        assertEquals(expected, actual);
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void getAdminShouldReturnAdminFromOptional() {
        Admin admin = new Admin();
        when(adminRepository.findById(5)).thenReturn(Optional.of(admin));

        Admin actual = adminServices.getAdmin(5);

        assertNotNull(actual);
        assertEquals(admin, actual);
        verify(adminRepository, times(1)).findById(5);
    }

    @Test
    void getAdminShouldThrowWhenOptionalIsEmpty() {
        when(adminRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class, () -> adminServices.getAdmin(99));
        verify(adminRepository, times(1)).findById(99);
    }

    @Test
    void updateShouldSaveWhenMatchingAdminExists() {
        Admin existing = new Admin();
        existing.setAdminId(10);

        Admin updated = new Admin();
        updated.setAdminId(10);

        doReturn(Collections.singletonList(existing)).when(adminRepository).findAll();

        adminServices.update(updated, 10);

        verify(adminRepository, times(1)).findAll();
        verify(adminRepository, times(1)).save(updated);
    }

    @Test
    void updateShouldNotSaveWhenNoMatchingAdminExists() {
        Admin existing = new Admin();
        existing.setAdminId(1);

        Admin updated = new Admin();
        updated.setAdminId(10);

        doReturn(Collections.singletonList(existing)).when(adminRepository).findAll();

        adminServices.update(updated, 10);

        verify(adminRepository, times(1)).findAll();
        verify(adminRepository, never()).save(updated);
    }

    @Test
    void deleteShouldDelegateToRepository() {
        adminServices.delete(7);

        verify(adminRepository, times(1)).deleteById(7);
        assertTrue(true);
    }

    @Test
    void addAdminShouldSaveAdmin() {
        Admin admin = new Admin();

        adminServices.addAdmin(admin);

        verify(adminRepository, times(1)).save(admin);
        assertNotNull(admin);
    }

    @Test
    void validateAdminCredentialsShouldReturnTrueForMatchingCredentials() {
        Admin admin = new Admin();
        admin.setAdminPassword("secret");
        when(adminRepository.findByAdminEmail("admin@example.com")).thenReturn(admin);

        boolean result = adminServices.validateAdminCredentials("admin@example.com", "secret");

        assertTrue(result);
        verify(adminRepository, times(1)).findByAdminEmail("admin@example.com");
    }

    @Test
    void validateAdminCredentialsShouldReturnFalseForWrongPassword() {
        Admin admin = new Admin();
        admin.setAdminPassword("secret");
        when(adminRepository.findByAdminEmail("admin@example.com")).thenReturn(admin);

        boolean result = adminServices.validateAdminCredentials("admin@example.com", "wrong");

        assertFalse(result);
        verify(adminRepository, times(1)).findByAdminEmail("admin@example.com");
    }

    @Test
    void validateAdminCredentialsShouldReturnFalseWhenAdminNotFound() {
        when(adminRepository.findByAdminEmail("missing@example.com")).thenReturn(null);

        boolean result = adminServices.validateAdminCredentials("missing@example.com", "secret");

        assertFalse(result);
        verify(adminRepository, times(1)).findByAdminEmail("missing@example.com");
    }
}