package com.ecommerce.searchservice.repository;
import com.ecommerce.searchservice.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    List<Product> findByCategory(List<String> category);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    @NonNull
    List<Product> findAll();
}
