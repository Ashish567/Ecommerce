package com.ecommerce.orderService.services;
import com.ecommerce.orderService.events.OrderEvent;
import com.ecommerce.orderService.models.Order;
import com.ecommerce.orderService.models.Status;
import com.ecommerce.orderService.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    // Create an order
    public Order createOrder(Order order) {
        order.setStatus(Status.CREATED);
        Order savedOrder = orderRepository.save(order);
        publishOrderEvent(savedOrder, "CREATED");
        return savedOrder;
    }

    // Get an order by ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Update order status
    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(Status.valueOf(status));
        publishOrderEvent(order, status);
        return orderRepository.save(order);
    }

    // Delete an order
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.deleteById(id);
        publishOrderEvent(order, "DELETED");
    }

    private void publishOrderEvent(Order order, String status) {
        OrderEvent event = new OrderEvent();
        event.setOrderId(order.getId());
        event.setUserId(order.getUserId());
        event.setStatus(status);
        event.setEventTime(LocalDateTime.now());

        kafkaProducerService.publishOrderEvent(event);
    }
}
