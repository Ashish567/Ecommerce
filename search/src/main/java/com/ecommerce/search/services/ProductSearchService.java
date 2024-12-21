package com.ecommerce.search.services;

import com.ecommerce.search.models.ProductDocument;
import com.ecommerce.search.repositories.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchService {

    private final ProductSearchRepository productRepository;

    @Autowired
    public ProductSearchService(ProductSearchRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDocument save(ProductDocument product) {
        return productRepository.save(product);
    }

    public List<ProductDocument> searchByName(String name) {
        return productRepository.findByName(name);
    }

    public List<ProductDocument> searchByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<ProductDocument> searchByNameAndCategory(String name, String category) {
        return productRepository.findByNameAndCategory(name, category);
    }
}
