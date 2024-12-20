package com.ecommerce.product.dtos;

import com.ecommerce.product.models.BaseModel;
import com.ecommerce.product.models.Product;
import com.ecommerce.product.models.Categories;
import com.ecommerce.product.repositories.CategoryRepository;
import jdk.jfr.Category;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
@Getter
@Setter
@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private int sku;
    private int quantity;
    private String brand;
    private boolean isActive;
    private double price;
    private Set<Long> categoryIds; // Only the IDs of the categories

    // Convert from entity to DTO
    public static ProductDto fromEntity(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setSku(product.getSku());
        dto.setQuantity(product.getQuantity());
        dto.setBrand(product.getBrand());
        dto.setActive(product.isActive());
        dto.setPrice(product.getPrice());

        // Extracting category IDs
        Set<Long> categoryIds = product.getCategories().stream()
                .map(BaseModel::getId)
                .collect(Collectors.toSet());
        dto.setCategoryIds(categoryIds);

        return dto;
    }

    // Convert from DTO to entity
    public Product toEntity() {
        Product product = new Product();
        product.setId(this.getId());
        product.setName(this.getName());
        product.setDescription(this.getDescription());
        product.setSku(this.getSku());
        product.setQuantity(this.getQuantity());
        product.setBrand(this.getBrand());
        product.setActive(this.isActive());
        product.setPrice(this.getPrice());

        product.setCategories(null);


        // Conversion of category IDs to Category entities should be handled in the service layer
        return product;
    }
}
