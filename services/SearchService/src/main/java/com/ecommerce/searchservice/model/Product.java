package com.ecommerce.searchservice.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(indexName = "products")
public class Product {
    @JsonProperty("id")
    @Field(type = FieldType.Long, name = "id")
    @Id
    private Long id;

    @JsonProperty("name")
    @Field(type = FieldType.Text, name = "name")
    private String name;

    @JsonProperty("description")
    @Field(type = FieldType.Text, name = "description")
    private String description;

    @JsonProperty("price")
    @Field(type = FieldType.Double, name = "price")
    private Double price;

    @JsonProperty("category")
    @Field(type = FieldType.Keyword, name = "category")
    private List<String> category;
    @Override public String toString() { return "Product{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", description='" + description + '\'' + ", price=" + price + ", category='" + category + '\'' + '}'; }

    @JsonProperty("sku")
    @Field(type = FieldType.Integer, name = "sku")
    private int sku;

    @JsonProperty("quantity")
    @Field(type = FieldType.Integer, name = "quantity")
    private int quantity;

    @JsonProperty("brand")
    @Field(type = FieldType.Text, name = "brand")
    private String brand;

    @JsonProperty("isActive")
    @Field(type = FieldType.Boolean, name = "isActive")
    private boolean isActive;
}
