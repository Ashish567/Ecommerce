package com.ecommerce.search.repositories;

import com.ecommerce.search.models.ProductDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, String> {

    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}, {\"nested\": {\"path\": \"categories\", \"query\": {\"match\": {\"categories.name\": \"?1\"}}}}]}}")
    List<ProductDocument> findByNameAndCategory(String name, String category);
    List<ProductDocument> findByName(String name);
}
