package com.foodies.fooddelivery.serviceImpl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foodies.fooddelivery.entity.Cart;
import com.foodies.fooddelivery.repository.CartRepository;
import com.foodies.fooddelivery.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart updateCart(Long id, Cart cart) {
        Cart existingCart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        existingCart.setUserId(cart.getUserId());
        existingCart.setTotalPrice(cart.getTotalPrice());

        return cartRepository.save(existingCart);
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
}