package com.ecommerce.search.repositories;

import com.ecommerce.search.models.CategoryDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorySearchRepository extends ElasticsearchRepository<CategoryDocument, String> {
    // Find by name
    List<CategoryDocument> findByName(String name);

    // Find by description
    List<CategoryDocument> findByDescription(String description);

    // Find by name and description
    List<CategoryDocument> findByNameAndDescription(String name, String description);
}
