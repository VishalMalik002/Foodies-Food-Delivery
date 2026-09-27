package com.foodies.fooddelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodies.fooddelivery.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}