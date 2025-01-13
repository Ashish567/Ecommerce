package com.ecommerce.product.controllers;

import com.ecommerce.product.dtos.CategoryDto;
import com.ecommerce.product.models.Categories;
import com.ecommerce.product.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        // Create a new category
        System.out.println(categoryDto.toString());
        Categories category = categoryService.createCategory(categoryDto.toEntity(categoryDto));

        return ResponseEntity.status(201).body(CategoryDto.fromEntity(category));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        // Update an existing category
        Categories updatedCategory = categoryService.updateCategory(id, categoryDto.toEntity(categoryDto));
        return ResponseEntity.ok(CategoryDto.fromEntity(updatedCategory));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        // Get a category by ID
        Categories category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(CategoryDto.fromEntity(category));
    }
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        // Get all categories
        List<Categories> categories = categoryService.getCategories();
        List<CategoryDto> categoryDtos = categories.stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDtos);
    }
}
