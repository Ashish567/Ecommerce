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
public class Categories extends BaseModel {
    private String name;
    private String description;
    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToMany(mappedBy = "categories") // Indicates the owning side is in Product
    private Set<Product> products;

    @Override
    public String toString() {
        return "Categories{" +
                "active=" + active +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", products=" + products +
                '}';
    }
}
