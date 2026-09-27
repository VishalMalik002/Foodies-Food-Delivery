package com.foodies.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import com.foodies.fooddelivery.entity.MenuItem;

public interface MenuItemService {

    MenuItem createMenuItem(MenuItem menuItem);

    Optional<MenuItem> getMenuItemById(Long id);

    List<MenuItem> getAllMenuItems();

    MenuItem updateMenuItem(Long id, MenuItem menuItem);

    void deleteMenuItem(Long id);

    List<MenuItem> getMenuItemsByRestaurant(Long restaurantId);
}