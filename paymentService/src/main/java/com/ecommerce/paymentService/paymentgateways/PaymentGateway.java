package com.ecommerce.paymentService.paymentgateways;

import com.ecommerce.paymentService.dtos.InitiatePayementDto;
import com.ecommerce.paymentService.dtos.PaymentResponseDTO;
import com.stripe.exception.StripeException;

public interface PaymentGateway {
    PaymentResponseDTO generatePaymentLink(InitiatePayementDto initiatePayementDto) throws StripeException;
}
