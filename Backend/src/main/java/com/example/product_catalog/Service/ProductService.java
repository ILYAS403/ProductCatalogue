package com.example.product_catalog.Service;

import com.example.product_catalog.Entity.Product;
import com.example.product_catalog.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Lister tous les produits
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Rechercher par nom ou par ID
    public List<Product> searchProducts(String query) {

        try {
            // Si query est un nombre, on considère que c'est un ID
            Long id = Long.parseLong(query);

            return productRepository.findById(id)
                    .map(List::of)
                    .orElseGet(() ->
                            productRepository.findByNameContainingIgnoreCase(query)
                    );

        } catch (NumberFormatException e) {
            // Si ce n'est pas un nombre, on recherche par son nom
            return productRepository.findByNameContainingIgnoreCase(query);
        }
    }

    // Ajouter un produit
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
}