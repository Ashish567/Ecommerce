package com.ecommerce.product.repositories;
import com.ecommerce.product.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categories, Long> {

}
