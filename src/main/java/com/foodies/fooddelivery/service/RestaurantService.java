package com.foodies.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import com.foodies.fooddelivery.entity.Restaurant;

public interface RestaurantService {

    Restaurant createRestaurant(Restaurant restaurant);

    Optional<Restaurant> getRestaurantById(Long id);

    List<Restaurant> getAllRestaurants();

    Restaurant updateRestaurant(Long id, Restaurant restaurant);

    void deleteRestaurant(Long id);
}