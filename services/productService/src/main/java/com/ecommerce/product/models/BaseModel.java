package com.ecommerce.product.models;

import com.ecommerce.product.dtos.ProductDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
    }
}
