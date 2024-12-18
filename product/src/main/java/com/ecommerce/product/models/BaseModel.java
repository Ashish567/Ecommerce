package com.ecommerce.product.models;

import jakarta.persistence.*;
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
}
