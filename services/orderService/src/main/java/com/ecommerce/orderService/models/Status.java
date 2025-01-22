package com.ecommerce.orderService.models;

public enum Status {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    CREATED("CREATED");

    Status(String cancelled) {
    }
}
