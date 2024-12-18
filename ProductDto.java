package com.ecommerce.product.dtos;

import com.ecommerce.product.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private int sku;
    private int quantity;
    private String brand;
    private boolean isActive;

    public static ProductDto fromEntity(Product product) {
        return new ProductDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getSku(),
            product.getQuantity(),
            product.getBrand(),
            product.isActive()
        );
    }

    public Product toEntity() {
        return new Product(
            this.id,
            this.name,
            this.description,
            this.sku,
            this.quantity,
            this.brand,
            this.isActive
        );
    }
}
