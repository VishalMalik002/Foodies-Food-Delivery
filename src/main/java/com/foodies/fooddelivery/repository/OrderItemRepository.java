package com.foodies.fooddelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodies.fooddelivery.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}