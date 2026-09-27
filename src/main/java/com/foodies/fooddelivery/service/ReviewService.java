package com.foodies.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import com.foodies.fooddelivery.entity.Review;

public interface ReviewService {

    Review createReview(Review review);

    List<Review> getReviewsByRestaurant(
            Long restaurantId
    );

    Optional<Review> getReviewById(
            Long id
    );

    boolean alreadyReviewed(
            Long userId,
            Long orderId
    );

    Double getAverageRating(
        Long restaurantId
    );
}