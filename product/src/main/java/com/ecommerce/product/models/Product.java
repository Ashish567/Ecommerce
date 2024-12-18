package com.ecommerce.product.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseModel {
    private String name;
    private String description;
    private int sku;
    private int quantity;
    private String brand;
    private boolean isActive;
    private double price;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_category", // Name of the join table
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), // Foreign key to Product
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id") // Foreign key to Categories
    )
    private Set<Categories> categories;

    public Product(Long id, String name, String description, int sku, int quantity, String brand, boolean isActive) {
        super();
    }
}
