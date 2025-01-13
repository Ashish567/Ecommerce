package com.ecommerce.orderService.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId; // Reference to User Microservice

    @Column(nullable = false)
    private String productId; // Reference to Product Microservice

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status; // e.g., "PENDING", "COMPLETED", "CANCELLED"

    private LocalDateTime orderDate = LocalDateTime.now();
}
