package com.example.product_catalog.Service;

import com.example.product_catalog.Entity.Product;
import com.example.product_catalog.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProductsShouldReturnAllProducts() {
        List<Product> products = List.of(product("Laptop"), product("Phone"));
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertSame(products, result);
        verify(productRepository).findAll();
    }

    @Test
    void searchProductsShouldReturnProductWhenNumericQueryMatchesId() {
        Product product = product("Laptop");
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        List<Product> result = productService.searchProducts("1");

        assertEquals(List.of(product), result);
        verify(productRepository).findById(1L);
        verify(productRepository, never()).findByNameContainingIgnoreCase("1");
    }

    @Test
    void searchProductsShouldSearchByNameWhenNumericIdDoesNotExist() {
        List<Product> products = List.of(product("Laptop"));
        when(productRepository.findById(99L)).thenReturn(Optional.empty());
        when(productRepository.findByNameContainingIgnoreCase("99")).thenReturn(products);

        List<Product> result = productService.searchProducts("99");

        assertSame(products, result);
        verify(productRepository).findById(99L);
        verify(productRepository).findByNameContainingIgnoreCase("99");
    }

    @Test
    void searchProductsShouldSearchByNameWhenQueryIsNotNumeric() {
        List<Product> products = List.of(product("Laptop"));
        when(productRepository.findByNameContainingIgnoreCase("lap")).thenReturn(products);

        List<Product> result = productService.searchProducts("lap");

        assertSame(products, result);
        verify(productRepository).findByNameContainingIgnoreCase("lap");
        verify(productRepository, never()).findById(anyLong());
    }

    @Test
    void addProductShouldSaveAndReturnProduct() {
        Product product = product("Laptop");
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.addProduct(product);

        assertSame(product, result);
        verify(productRepository).save(product);
    }

    private Product product(String name) {
        return new Product(
                name,
                "Electronics",
                BigDecimal.valueOf(999.99),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
    }
}
