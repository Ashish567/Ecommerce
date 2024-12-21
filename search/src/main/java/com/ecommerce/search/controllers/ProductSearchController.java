package com.ecommerce.search.controllers;

import com.ecommerce.search.models.ProductDocument;
import com.ecommerce.search.services.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class ProductSearchController {

    private final ProductSearchService searchService;

    @Autowired
    public ProductSearchController(ProductSearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/index")
    public ProductDocument indexProduct(@RequestBody ProductDocument product) {
        return searchService.save(product);
    }

    @GetMapping("/name")
    public List<ProductDocument> searchByName(@RequestParam String name) {
        return searchService.searchByName(name);
    }

    @GetMapping("/category")
    public List<ProductDocument> searchByCategory(@RequestParam String category) {
        return searchService.searchByCategory(category);
    }

    @GetMapping
    public List<ProductDocument> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category
    ) {
        if (name != null && category != null) {
            return searchService.searchByNameAndCategory(name, category);
        } else if (name != null) {
            return searchService.searchByName(name);
        } else if (category != null) {
            return searchService.searchByCategory(category);
        }
        return List.of(); // Return an empty list if no filters are provided
    }
}
