package com.foodies.fooddelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodies.fooddelivery.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}