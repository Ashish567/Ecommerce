package com.ecommerce.search.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products")
public class ProductDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Integer)
    private int sku;

    @Field(type = FieldType.Integer)
    private int quantity;

    @Field(type = FieldType.Text)
    private String brand;

    @Field(type = FieldType.Boolean)
    private boolean isActive;

    @Field(type = FieldType.Double)
    private double price;

    @Field(type = FieldType.Nested)
    private Set<CategoryDocument> categories;

    @Version
    private Long version;

    @Override
    public String toString() {
        return "ProductDocument{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sku=" + sku +
                ", quantity=" + quantity +
                ", brand='" + brand + '\'' +
                ", isActive=" + isActive +
                ", price=" + price +
                ", categories=" + categories +
                ", version=" + version +
                '}';
    }
}
