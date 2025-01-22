package com.ecommerce.searchservice.dto;

import com.ecommerce.searchservice.model.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@Data
public class ProductDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private int sku;
    private int quantity;
    private String brand;
    private boolean isActive;
    private double price;
    private List<Long> categoryIds; // Only the IDs of the categories
    @Getter
    private String operation;

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
        List<Long> categoryIds = product.getCategory().stream()
                        .map(Long::valueOf)
                        .collect(Collectors.toList());
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

        product.setCategory(this.getCategoryIds().stream()
                .map(String::valueOf)
                .collect(Collectors.toList()));


        // Conversion of category IDs to Category entities should be handled in the service layer
        return product;
    }
}
