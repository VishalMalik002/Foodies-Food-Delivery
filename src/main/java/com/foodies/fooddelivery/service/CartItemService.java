package com.foodies.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import com.foodies.fooddelivery.entity.CartItem;

public interface CartItemService {

    CartItem addCartItem(CartItem cartItem);

    Optional<CartItem> getCartItemById(Long id);

    List<CartItem> getAllCartItems();

    CartItem updateCartItem(Long id, CartItem cartItem);

    void deleteCartItem(Long id);
}