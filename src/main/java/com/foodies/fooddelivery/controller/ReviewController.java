package com.foodies.fooddelivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodies.fooddelivery.entity.Review;
import com.foodies.fooddelivery.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(
            ReviewService reviewService) {

        this.reviewService =
                reviewService;
    }

    @PostMapping
    public ResponseEntity<?> createReview(
            @RequestBody Review review) {

        try {

            return ResponseEntity.ok(
                    reviewService.createReview(review)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Review>>
            getRestaurantReviews(
                    @PathVariable Long restaurantId) {

        return ResponseEntity.ok(
                reviewService
                        .getReviewsByRestaurant(
                                restaurantId
                        )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review>
            getReviewById(
                    @PathVariable Long id) {

        return reviewService
                .getReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(
                    ResponseEntity
                        .notFound()
                        .build()
                );
    }

    @GetMapping(
        "/check/{userId}/{orderId}"
    )
    public ResponseEntity<Boolean>
            checkAlreadyReviewed(
                    @PathVariable Long userId,
                    @PathVariable Long orderId) {

        return ResponseEntity.ok(
                reviewService
                        .alreadyReviewed(
                                userId,
                                orderId
                        )
        );
    }

    @GetMapping("/restaurant/{restaurantId}/average")
        public ResponseEntity<Double> getAverageRating(
                @PathVariable Long restaurantId) {

        return ResponseEntity.ok(
                reviewService.getAverageRating(restaurantId)
        );
        }
}