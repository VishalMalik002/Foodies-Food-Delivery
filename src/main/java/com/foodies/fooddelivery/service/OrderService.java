package com.foodies.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import com.foodies.fooddelivery.entity.Order;

public interface OrderService {

    Order placeOrder(Order order);

    Optional<Order> getOrderById(Long id);

    List<Order> getOrdersByUserId(Long userId);
    List<Order> getAllOrders();

    Order updateOrderStatus(Long id, String status);

    void cancelOrder(Long id);
    Order assignDeliveryPartner(Long orderId, Long deliveryPartnerId);

    List<Order> getOrdersByDeliveryPartnerId(Long deliveryPartnerId);

}