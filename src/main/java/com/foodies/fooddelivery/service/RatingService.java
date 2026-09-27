package com.foodies.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import com.foodies.fooddelivery.entity.Rating;

public interface RatingService {

    Rating createRating(Rating rating);

    Optional<Rating> getRatingById(Long id);

    List<Rating> getAllRatings();

    List<Rating> getRatingsByRestaurantId(Long restaurantId);

    Rating updateRating(Long id, Rating rating);

    void deleteRating(Long id);
}