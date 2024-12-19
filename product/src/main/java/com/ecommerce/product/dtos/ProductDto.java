package com.ecommerce.product.dtos;

import com.ecommerce.product.models.Product;
import lombok.*;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Data
@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private int sku;
    private int quantity;
    private String brand;
    private boolean active;
    private double price;


    public static ProductDto fromEntity(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setSku(product.getSku());
        productDto.setQuantity(product.getQuantity());
        productDto.setBrand(product.getBrand());
        productDto.setActive(product.isActive());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    public Product toEntity() {
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setDescription(this.description);
        product.setSku(this.sku);
        product.setQuantity(this.quantity);
        product.setBrand(this.brand);
        product.setActive(this.active);
        product.setPrice(this.price);
        return product;
    }
}
