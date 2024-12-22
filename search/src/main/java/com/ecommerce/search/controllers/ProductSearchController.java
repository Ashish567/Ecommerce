package com.ecommerce.search.controllers;

import com.ecommerce.search.models.ProductDocument;
import com.ecommerce.search.services.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductSearchController {

    private final ProductSearchService searchService;

    @Autowired
    public ProductSearchController(ProductSearchService searchService) {
        this.searchService = searchService;
    }

    // Index a new product
    @PostMapping("/index")
    public ResponseEntity<?> indexProduct(@RequestBody ProductDocument product) {
        // Validate required fields
        if (product == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Product cannot be null");
        }

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Product name is required");
        }

        if (product.getBrand() == null || product.getBrand().trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Product brand is required");
        }

        if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Product description is required");
        }

        // Set default values for optional fields if they're null
        if (product.getCategories() == null) {
            product.setCategories(null);
        }

        try {
            ProductDocument indexedProduct = searchService.save(product);
            return ResponseEntity.ok(indexedProduct);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error indexing product: " + e.getMessage());
        }
    }

    // Search products by name
    @GetMapping("/search-by-name")
    public ResponseEntity<?> searchByName(@RequestParam String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Search name cannot be empty");
        }

        try {
            List<ProductDocument> results = searchService.searchByName(name.trim());
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error searching products: " + e.getMessage());
        }
    }

    // Search products by multiple fields
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category
    ) {
        try {
            List<ProductDocument> results;

            if (name != null && !name.trim().isEmpty() &&
                    category != null && !category.trim().isEmpty()) {
                results = searchService.searchByNameAndCategory(name.trim(), category.trim());
            } else if (name != null && !name.trim().isEmpty()) {
                results = searchService.searchByName(name.trim());
            } else if (category != null && !category.trim().isEmpty()) {
                // Add a method in your service to search by category if needed
//                results = searchService.searchByCategory(category.trim());
                results = List.of();
            } else {
                return ResponseEntity
                        .badRequest()
                        .body("At least one search parameter (name or category) is required");
            }

            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error searching products: " + e.getMessage());
        }
    }
}