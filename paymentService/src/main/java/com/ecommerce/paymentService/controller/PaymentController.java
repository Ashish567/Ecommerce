package com.ecommerce.paymentService.controller;

import com.ecommerce.paymentService.dtos.InitiatePayementDto;
import com.ecommerce.paymentService.dtos.PaymentResponseDTO;
import com.ecommerce.paymentService.services.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> initiatePayment(@RequestBody InitiatePayementDto initiatePayementDto) throws StripeException {
        return ResponseEntity.ok(paymentService.initiatePayment(initiatePayementDto));
    }

    @PostMapping("/webhook")
    public String listenToWebhook(@RequestBody String webhookEvent) {
        System.out.println(webhookEvent);
        return "OK";
    }
    @GetMapping
    public String hello() {
        return "Hello from Payment Service";
    }
}
