package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.entities.Product;
import com.example.demo.services.ProductServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductServices productServices;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
    }

    @Test
    void addProductShouldDelegateToServiceAndRedirect() {
        String viewName = productController.addProduct(product);

        assertEquals("redirect:/admin/services", viewName);
        verify(productServices).addProduct(product);
    }

    @Test
    void updateProductShouldDelegateToServiceAndRedirect() {
        String viewName = productController.updateProduct(product, 42);

        assertEquals("redirect:/admin/services", viewName);
        verify(productServices).updateproduct(product, 42);
    }

    @Test
    void deleteShouldDelegateToServiceAndRedirect() {
        String viewName = productController.delete(7);

        assertEquals("redirect:/admin/services", viewName);
        verify(productServices).deleteProduct(7);
    }

    @Test
    void controllerShouldBeInjected() {
        assertNotNull(productController);
    }
}