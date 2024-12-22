package com.ecommerce.search.services;

import com.ecommerce.search.models.CategoryDocument;
import com.ecommerce.search.repositories.CategorySearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorySearchService {

    private final CategorySearchRepository categorySearchRepository;

    @Autowired
    public CategorySearchService(CategorySearchRepository categorySearchRepository) {
        this.categorySearchRepository = categorySearchRepository;
    }

    // Save a category document
    public CategoryDocument save(CategoryDocument category) {
        return categorySearchRepository.save(category);
    }

    // Search by name
    public List<CategoryDocument> searchByName(String name) {
        return categorySearchRepository.findByName(name);
    }

    // Search by description
    public List<CategoryDocument> searchByDescription(String description) {
        return categorySearchRepository.findByDescription(description);
    }

    // Search by both name and description
    public List<CategoryDocument> searchByNameAndDescription(String name, String description) {
        return categorySearchRepository.findByNameAndDescription(name, description);
    }
}
