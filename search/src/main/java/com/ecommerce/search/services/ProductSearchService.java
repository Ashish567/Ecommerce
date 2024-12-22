package com.ecommerce.search.services;

import com.ecommerce.search.models.ProductDocument;
import com.ecommerce.search.repositories.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
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

    public List<ProductDocument> searchByNameAndCategory(String name, String category) {
        return productRepository.findByNameAndCategory(name, category);
    }

    public List<ProductDocument> searchByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @KafkaListener(topics = "product-to-search", groupId = "search-consumer-group")
    public void listenProductDetails(String productDetails) {
        System.out.println("Received Product Details: " + productDetails);
        // Add logic for indexing/searching the product in the search database
    }
}
