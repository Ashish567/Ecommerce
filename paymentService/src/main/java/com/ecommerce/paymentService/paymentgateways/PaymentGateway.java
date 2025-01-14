package com.ecommerce.paymentService.paymentgateways;

import com.stripe.exception.StripeException;

public interface PaymentGateway {
    public String generatePaymentLink(String email, String phoneNumber, Long amount, String orderId) throws StripeException;
}
