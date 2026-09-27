package com.foodies.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import com.foodies.fooddelivery.entity.OrderItem;

public interface OrderItemService {

    OrderItem addOrderItem(OrderItem orderItem);

    Optional<OrderItem> getOrderItemById(Long id);

    List<OrderItem> getAllOrderItems();

    OrderItem updateOrderItem(Long id, OrderItem orderItem);

    void deleteOrderItem(Long id);
}