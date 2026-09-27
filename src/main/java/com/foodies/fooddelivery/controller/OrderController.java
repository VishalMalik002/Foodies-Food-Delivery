package com.foodies.fooddelivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodies.fooddelivery.entity.Order;
import com.foodies.fooddelivery.entity.User;
import com.foodies.fooddelivery.repository.UserRepository;
import com.foodies.fooddelivery.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(
            OrderService orderService,
            UserRepository userRepository) {

        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(
            @RequestBody Order order,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        order.setUserId(user.getId());

        if (order.getDeliveryAddress() == null ||
                order.getDeliveryAddress().isBlank()) {

            order.setDeliveryAddress(user.getAddress());
        }

        if (order.getStatus() == null ||
                order.getStatus().isBlank()) {

            order.setStatus("PLACED");
        }

        if (order.getPaymentStatus() == null ||
                order.getPaymentStatus().isBlank()) {

            order.setPaymentStatus("PENDING");
        }

        return ResponseEntity.ok(
                orderService.placeOrder(order)
        );
    }

        @GetMapping
        public ResponseEntity<List<Order>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
        }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable Long id) {

        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.getOrdersByUserId(userId)
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(id, status)
        );
    }

    @PutMapping("/{orderId}/assign-delivery-partner/{deliveryPartnerId}")
    public ResponseEntity<Order> assignDeliveryPartner(
            @PathVariable Long orderId,
            @PathVariable Long deliveryPartnerId) {

        return ResponseEntity.ok(
                orderService.assignDeliveryPartner(
                        orderId,
                        deliveryPartnerId
                )
        );
    }

    @GetMapping("/delivery-partner/{deliveryPartnerId}")
    public ResponseEntity<List<Order>> getOrdersByDeliveryPartnerId(
            @PathVariable Long deliveryPartnerId) {

        return ResponseEntity.ok(
                orderService.getOrdersByDeliveryPartnerId(
                        deliveryPartnerId
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long id) {

        orderService.cancelOrder(id);

        return ResponseEntity.noContent().build();
    }
}