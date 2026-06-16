package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServicesTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServices productServices;

    @Test
    void addProductShouldDelegateToRepositorySave() {
        Product product = new Product();

        productServices.addProduct(product);

        verify(productRepository, times(1)).save(product);
        assertNotNull(productServices);
    }

    @Test
    void getAllProductsShouldReturnRepositoryResults() {
        Product p1 = new Product();
        Product p2 = new Product();
        List<Product> expected = Arrays.asList(p1, p2);
        doReturn(expected).when(productRepository).findAll();

        List<Product> actual = productServices.getAllProducts();

        assertEquals(expected, actual);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductShouldReturnProductFromOptional() {
        Product product = new Product();
        doReturn(Optional.of(product)).when(productRepository).findById(1);

        Product actual = productServices.getProduct(1);

        assertEquals(product, actual);
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void updateProductShouldSetIdAndSaveWhenExistingProductMatchesId() {
        Product existing = new Product();
        existing.setPid(7);
        Product updated = new Product();
        doReturn(Optional.of(existing)).when(productRepository).findById(7);

        productServices.updateproduct(updated, 7);

        assertEquals(7, updated.getPid());
        verify(productRepository, times(1)).findById(7);
        verify(productRepository, times(1)).save(updated);
    }

    @Test
    void deleteProductShouldDelegateToRepositoryDeleteById() {
        productServices.deleteProduct(3);

        verify(productRepository, times(1)).deleteById(3);
        assertNotNull(productServices);
    }

    @Test
    void getProductByNameShouldReturnProductWhenFound() {
        Product product = new Product();
        doReturn(product).when(productRepository).findByPname("Laptop");

        Product actual = productServices.getProductByName("Laptop");

        assertEquals(product, actual);
        verify(productRepository, times(1)).findByPname("Laptop");
    }

    @Test
    void getProductByNameShouldReturnNullWhenNotFound() {
        doReturn(null).when(productRepository).findByPname("Missing");

        Product actual = productServices.getProductByName("Missing");

        assertNull(actual);
        verify(productRepository, times(1)).findByPname("Missing");
    }

    @Test
    void getAllProductsShouldHandleEmptyList() {
        doReturn(Collections.emptyList()).when(productRepository).findAll();

        List<Product> actual = productServices.getAllProducts();

        assertEquals(Collections.emptyList(), actual);
        verify(productRepository, times(1)).findAll();
    }
}