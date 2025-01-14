package com.ecommerce.paymentService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
public class StripeClientConfig {
    @Value("${stripe.key.secret}")
    private String razorpaySecret;

    @Value("${stripe.key.id}")
    private String razorpayId;

    @Bean
    public RazorpayClient createRazorpayClient() throws RazorpayException {
        return new RazorpayClient(razorpayId, razorpaySecret);
}
