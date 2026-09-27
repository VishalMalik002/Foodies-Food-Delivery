package com.foodies.fooddelivery.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foodies.fooddelivery.entity.CartItem;
import com.foodies.fooddelivery.repository.CartItemRepository;
import com.foodies.fooddelivery.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Optional<CartItem> getCartItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem updateCartItem(Long id, CartItem cartItem) {
        CartItem existingCartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        existingCartItem.setCartId(cartItem.getCartId());
        existingCartItem.setMenuItemId(cartItem.getMenuItemId());
        existingCartItem.setQuantity(cartItem.getQuantity());
        existingCartItem.setSubtotal(cartItem.getSubtotal());

        return cartItemRepository.save(existingCartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}