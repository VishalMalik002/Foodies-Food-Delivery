package com.foodies.fooddelivery.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foodies.fooddelivery.entity.Rating;
import com.foodies.fooddelivery.repository.RatingRepository;
import com.foodies.fooddelivery.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Optional<Rating> getRatingById(Long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingsByRestaurantId(Long restaurantId) {
        return ratingRepository.findAll()
                .stream()
                .filter(rating -> rating.getRestaurantId().equals(restaurantId))
                .toList();
    }

    @Override
    public Rating updateRating(Long id, Rating rating) {
        Rating existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found"));

        existingRating.setUserId(rating.getUserId());
        existingRating.setRestaurantId(rating.getRestaurantId());
        existingRating.setStars(rating.getStars());
        existingRating.setReview(rating.getReview());

        return ratingRepository.save(existingRating);
    }

    @Override
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }
}