package com.foodies.fooddelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodies.fooddelivery.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}