package com.ecommerce.paymentService.controller;

import com.ecommerce.paymentService.dtos.InitiatePayementDto;
import com.ecommerce.paymentService.services.PaymentService;
import com.ecommerce.paymentService.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;
    private ProductService productService;

    public PaymentController(PaymentService paymentService,
                             ProductService productService) {
        this.paymentService = paymentService;
        this.productService = productService;
    }

    @PostMapping
    public String initiatePayment(@RequestBody InitiatePayementDto initiatePayementDto) {

        return paymentService.initiatePayment(
                initiatePayementDto.getEmail(),
                initiatePayementDto.getPhoneNumber(),
                initiatePayementDto.getAmount(),
                initiatePayementDto.getOrderId());
    }

    @PostMapping("/webhook")
    public String listenToWebhook(@RequestBody String webhookEvent) {
        System.out.println(webhookEvent);
        return "OK";
    }

    @GetMapping("/product/{id}")
    public String getProductDetails(@PathVariable String id) {
        return productService.getProductDetails(id);
    }
}
