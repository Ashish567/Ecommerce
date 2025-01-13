package com.ecommerce.product.services;

import com.ecommerce.product.models.Categories;
import com.ecommerce.product.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("categoryService")
public class CategoryService {
    private final CategoryRepository categoryRepository;
    CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public Categories createCategory(Categories category) {
        System.out.println(category.toString());
        return categoryRepository.save(category);
    }

    public Categories updateCategory(Long id, Categories entity) {
        Categories category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        category.setName(entity.getName());
        category.setDescription(entity.getDescription());
        category.setActive(entity.isActive());
        return categoryRepository.save(category);
    }

    public Categories getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Categories> getCategories() {
        return categoryRepository.findAll();
    }
}
