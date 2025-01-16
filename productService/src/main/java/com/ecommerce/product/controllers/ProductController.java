package com.ecommerce.product.controllers;

import com.ecommerce.product.dtos.ProductDto;
import com.ecommerce.product.dtos.ProductSearchCriteria;
import com.ecommerce.product.models.Product;
import com.ecommerce.product.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
//import java.awt.print.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
//    @GetMapping
//    public ResponseEntity<List<ProductDto>> getProducts() {
//        List<Product> products = productService.getProducts();
//        List<ProductDto> productDtos = products.stream()
//                .map(ProductDto::fromEntity)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(productDtos);
//    }

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
    @GetMapping
    public ResponseEntity<Page<ProductDto>> getProducts(
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {

        ProductSearchCriteria criteria = ProductSearchCriteria.builder()
                .categories(categories)
                .brand(brand)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .isActive(isActive)
                .build();

        // Create sort orders from the sort parameter
        List<Sort.Order> orders = new ArrayList<>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(
                        _sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                        _sort[0]
                ));
            }
        } else {
            orders.add(new Sort.Order(
                    sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                    sort[0]
            ));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

        Page<Product> products = productService.findProductsWithFilters(criteria, pageable);
        Page<ProductDto> productDtos = products.map(ProductDto::fromEntity);

        return ResponseEntity.ok(productDtos);
    }

}
