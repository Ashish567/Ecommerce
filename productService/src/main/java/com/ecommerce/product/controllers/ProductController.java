package com.ecommerce.product.controllers;

import com.ecommerce.product.dtos.ProductDto;
import com.ecommerce.product.models.Product;
import com.ecommerce.product.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = ProductDto.fromEntity(product);
            return ResponseEntity.ok(productDto);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Get All Products
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<Product> products = productService.getProducts();
        List<ProductDto> productDtos = products.stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    // Save a New Product
    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto) {
        System.out.println(productDto.toString());
        Product savedProduct = productService.saveProduct(productDto);
        return ResponseEntity.status(201).body(ProductDto.fromEntity(savedProduct));
    }

    // Update an Existing Product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDto.toEntity());
            return ResponseEntity.ok(ProductDto.fromEntity(updatedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Delete a Product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Product not found");
        }
    }
}
