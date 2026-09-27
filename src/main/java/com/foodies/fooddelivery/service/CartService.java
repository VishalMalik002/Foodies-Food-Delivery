package com.foodies.fooddelivery.service;

import java.util.Optional;

import com.foodies.fooddelivery.entity.Cart;

public interface CartService {

    Cart createCart(Cart cart);

    Optional<Cart> getCartById(Long id);

    Cart updateCart(Long id, Cart cart);

    void deleteCart(Long id);
}