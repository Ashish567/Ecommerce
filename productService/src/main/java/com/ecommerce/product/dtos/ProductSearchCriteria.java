package com.ecommerce.product.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
@Getter
@Setter
public class ProductSearchCriteria {
    private List<String> categories;
    private String brand;
    private Double minPrice;
    private Double maxPrice;
    private Boolean isActive;
}
