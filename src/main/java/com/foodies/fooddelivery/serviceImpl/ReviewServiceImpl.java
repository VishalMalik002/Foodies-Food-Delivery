package com.foodies.fooddelivery.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foodies.fooddelivery.entity.Review;
import com.foodies.fooddelivery.repository.ReviewRepository;
import com.foodies.fooddelivery.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository) {

        this.reviewRepository =
                reviewRepository;
    }

    @Override
    public Review createReview(Review review) {

        if (review.getRating() == null ||
            review.getRating() < 1 ||
            review.getRating() > 5) {

            throw new RuntimeException(
                    "Rating must be between 1 and 5"
            );
        }

        if (review.getUserId() == null ||
            review.getOrderId() == null ||
            review.getRestaurantId() == null) {

            throw new RuntimeException(
                    "User, order and restaurant are required"
            );
        }

        if (reviewRepository.existsByUserIdAndOrderId(
                review.getUserId(),
                review.getOrderId())) {

            throw new RuntimeException(
                    "You have already reviewed this order"
            );
        }

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByRestaurant(
            Long restaurantId) {

        return reviewRepository
                .findByRestaurantId(restaurantId);
    }

    @Override
    public Optional<Review> getReviewById(
            Long id) {

        return reviewRepository.findById(id);
    }

    @Override
    public boolean alreadyReviewed(
            Long userId,
            Long orderId) {

        return reviewRepository
                .existsByUserIdAndOrderId(
                        userId,
                        orderId
                );
    }

    @Override
        public Double getAverageRating(Long restaurantId) {
        return reviewRepository.findAverageRatingByRestaurantId(restaurantId);
        }
}