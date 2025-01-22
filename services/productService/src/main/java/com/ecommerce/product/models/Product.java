package com.ecommerce.product.models;

import com.ecommerce.product.dtos.ProductDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;


//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Setter
@Getter
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

    @Version
    private Long version;

    @Override
    public String toString() {
        return "Product{" +
                "brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sku=" + sku +
                ", quantity=" + quantity +
                ", isActive=" + isActive +
                ", price=" + price +
                ", categories=" + categories +
                '}';
    }

    public ProductDto toDto() {
        ProductDto productDto = new ProductDto(id);
        productDto.setId(this.getId());
        productDto.setBrand(this.getBrand());
        productDto.setName(this.getName());
        productDto.setDescription(this.getDescription());
        productDto.setSku(this.getSku());
        productDto.setQuantity(this.getQuantity());
        productDto.setActive(this.isActive());
        productDto.setPrice(this.getPrice());
        productDto.setCategoryIds(this.getCategories().stream().map(Categories::getId).collect(Collectors.toSet()));
        return productDto;
    }
}
