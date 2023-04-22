package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Product;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for ProductRepository")
class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Positive test. Find all products.")
    void testFindAll() {
        List<Product> products = new ArrayList<>(List.of(
                EntityCreator.getProduct(UUID.randomUUID())
        ));

        when(productRepository.findAll()).thenReturn(products);
        List<Product> foundProducts = productRepository.findAll();

        assertEquals(products.size(), foundProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Positive test. Find product by Id.")
    void testFindProductById() {
        Product product = EntityCreator.getProduct(UUID.randomUUID());

        when(productRepository.findProductById(product.getId())).thenReturn(Optional.of(product));
        Optional<Product> foundProduct = productRepository.findProductById(product.getId());

        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
    }
}