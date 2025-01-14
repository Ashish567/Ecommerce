package com.ecommerce.paymentService.services;


import com.ecommerce.paymentService.paymentgateways.PaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private PaymentGateway paymentGateway;

    public PaymentService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }
    public String initiatePayment(String email, String phoneNumber, Long amount, String orderId) {
        /*
        1. Call Order Service
        2. Get Order details
        3. Verify amount
        4. Call PG to generate payment link
        5. return paymentlink
         */
        return paymentGateway.generatePaymentLink(email, phoneNumber, amount, orderId);

    }
}