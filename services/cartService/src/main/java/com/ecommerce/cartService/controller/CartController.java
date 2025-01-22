package com.ecommerce.cartService.controller;
import com.ecommerce.cartService.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart/")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addItemToCart(
            @PathVariable String userId,
            @RequestParam String productId,
            @RequestParam int quantity) {
        cartService.addItemToCart(userId, productId, quantity);
        return ResponseEntity.ok(
                String.format("Item added to cart: %s, quantity: %d", productId, quantity)
        );
    }

    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<String> removeItemFromCart(
            @PathVariable String userId,
            @RequestParam String productId) {
        cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok(
                String.format("Item removed from cart: %s", productId)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Integer>> getCart(@PathVariable String userId) {
        return ResponseEntity.ok(
                cartService.getCart(userId)
        );
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(
                String.format("Cart cleared for user: %s", userId)
        );
    }
}
