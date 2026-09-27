package com.foodies.fooddelivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foodies.fooddelivery.entity.Review;

public interface ReviewRepository
        extends JpaRepository<Review, Long> {

    List<Review> findByRestaurantId(Long restaurantId);

    boolean existsByUserIdAndOrderId(
            Long userId,
            Long orderId
    );

    @Query("""
        SELECT COALESCE(AVG(r.rating), 0)
        FROM Review r
        WHERE r.restaurantId = :restaurantId
    """)
    Double findAverageRatingByRestaurantId(
            @Param("restaurantId") Long restaurantId
    );
}