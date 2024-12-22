package com.ecommerce.search.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "categories")
public class CategoryDocument {
    private String id;
    private String name;
    private String description;
    private String parentCategoryId;
}
