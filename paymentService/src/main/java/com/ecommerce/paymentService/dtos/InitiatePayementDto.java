package com.ecommerce.paymentService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePayementDto {
    private String email;
    private String phoneNumber;
    private String orderId;
    private Long amount;
}