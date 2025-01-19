package com.ecommerce.searchservice.service;

import com.ecommerce.searchservice.model.Product;
import com.ecommerce.searchservice.repository.ProductRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    ProductService(ProductRepository productRepository, ElasticsearchOperations elasticsearchOperations) {
        this.productRepository = productRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public Product saveProduct(Product product) {
        System.out.println("Product: {}" + product);
        return productRepository.save(product);
    }

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    public List<Product> searchProducts(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return List.of(); // Return an empty list if the search term is null or empty
        }

        // Create a Criteria for searching in multiple fields
        Criteria criteria = new Criteria("name").contains(searchTerm)
                .or(new Criteria("description").contains(searchTerm))
                .or(new Criteria("category").contains(searchTerm));

        // Create a CriteriaQuery with the defined criteria
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);

        // Execute the search
        SearchHits<Product> searchHits = elasticsearchOperations.search(criteriaQuery, Product.class);

        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

public List<Product> findByCategory(List<String> category) {
    return productRepository.findByCategory(category);
}

public List<Product> findByPriceRange(Double minPrice, Double maxPrice) {
    return productRepository.findByPriceBetween(minPrice, maxPrice);
}
    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        logger.info("Fetched products: {}", products);
        return products;
    }

}
