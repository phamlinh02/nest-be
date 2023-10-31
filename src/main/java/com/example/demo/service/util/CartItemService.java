package com.example.demo.service.util;

import com.example.demo.domain.CartItem;

import java.util.List;

public interface CartItemService {
    public List<CartItem> findByIdAccount(Long accountId);

    public CartItem findByAccountIdAndProductId(Long accountId, Long productId);

    public CartItem addToCart(CartItem item);

    public List<CartItem> remove(Long accountId);

    public void removeById(Long id);

    CartItem updateCart(Long id, Long quantity);

}
