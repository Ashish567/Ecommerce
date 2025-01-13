package com.ecommerce.orderService.events;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderEvent {
    private Long orderId;
    private String userId;
    private String status; // e.g., CREATED, COMPLETED
    private LocalDateTime eventTime;
}
