package com.ecommerce.search.repositories;

import com.ecommerce.search.models.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, String> {
    List<ProductDocument> findByName(String name);
    List<ProductDocument> findByCategory(String category);
    List<ProductDocument> findByNameAndCategory(String name, String category);
}
