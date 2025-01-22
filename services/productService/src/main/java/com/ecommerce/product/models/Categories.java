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

    public Long getId() {
        return id;
    }
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

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
