package com.ecommerce.product.dtos;
import java.util.Set;

import com.ecommerce.product.models.Categories;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private boolean active;
//    private Set<Long> productIds; // Only the IDs of the products // Convert from entity to DTO public static CategoriesDto fromEntity(Categories category) { CategoriesDto dto = new CategoriesDto(); dto.setId(category.getId()); dto.setName(category.getName()); dto.setDescription(category.getDescription()); dto.setActive(category.isActive()); // Extracting product IDs Set<Long> productIds = category.getProducts().stream() .map(product -> product.getId()) .collect(Collectors.toSet()); dto.setProductIds(productIds); return dto; } // Convert from DTO to entity public Categories toEntity() { Categories category = new Categories(); category.setId(this.getId()); category.setName(this.getName()); category.setDescription(this.getDescription()); category.setActive(this.isActive()); // Conversion of product IDs to Product entities should be handled in service layer return category; }

    public static CategoryDto fromEntity(Categories category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setActive(category.isActive());
        return dto;
    }

    public Categories toEntity(CategoryDto categoryDto) {
        Categories category = new Categories();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setActive(categoryDto.isActive());
        return category;
    }

}
