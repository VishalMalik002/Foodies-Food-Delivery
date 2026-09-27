package com.foodies.fooddelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodies.fooddelivery.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}