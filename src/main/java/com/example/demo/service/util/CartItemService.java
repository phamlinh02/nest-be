package com.example.demo.service.util;

import com.example.demo.domain.CartItem;

import java.util.List;

public interface CartItemService {
    public List<CartItem> findByIdAccount(Long accountId);

    public CartItem addToCart(CartItem item);

    public List<CartItem> remove(Long accountId);

    public void removeById(Long id);

    public void updateCartItems(Long accountId, List<CartItem> cartItems);



}
