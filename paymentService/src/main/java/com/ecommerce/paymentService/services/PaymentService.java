package com.ecommerce.paymentService.services;

import com.ecommerce.paymentService.dtos.InitiatePayementDto;
import com.ecommerce.paymentService.dtos.PaymentResponseDTO;
import com.ecommerce.paymentService.paymentgateways.PaymentGateway;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Value("${stripe.successUrl")
    static String apiKey;
    private final PaymentGateway paymentGateway;

    public PaymentService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }
    public PaymentResponseDTO initiatePayment(InitiatePayementDto initiatePayementDto) throws StripeException {
        Stripe.apiKey = apiKey;
        return paymentGateway.generatePaymentLink(initiatePayementDto);
    }
}