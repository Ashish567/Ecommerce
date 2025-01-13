package com.ecommerce.cartService.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CartService {

    private static final String CART_KEY_PREFIX = "cart:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, Integer> hashOperations;

    @Autowired
    public CartService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    // Add item to cart
    public void addItemToCart(String userId, String productId, int quantity) {
        String key = CART_KEY_PREFIX + userId;
        hashOperations.put(key, productId, hashOperations.hasKey(key,productId) ? hashOperations.get(key, productId) : 0 + quantity);
    }

    // Remove item from cart
    public void removeItemFromCart(String userId, String productId) {
        String key = CART_KEY_PREFIX + userId;
        hashOperations.delete(key, productId);
    }

    // Get all items in cart
    public Map<String, Integer> getCart(String userId) {
        String key = CART_KEY_PREFIX + userId;
        return hashOperations.entries(key);
    }

    // Clear cart
    public void clearCart(String userId) {
        String key = CART_KEY_PREFIX + userId;
        redisTemplate.delete(key);
    }
}
