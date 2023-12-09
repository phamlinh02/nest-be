package com.example.demo.service.util;

import com.example.demo.domain.CartItem;
import com.example.demo.domain.Product;
import com.example.demo.service.dto.cart.CartItemDTO;
import com.example.demo.service.dto.product.ProductDTO;

import java.util.List;

public interface CartItemService {
    public List<CartItem> findByIdAccount(Long accountId);

    public CartItem addToCart(CartItem item);

    public List<CartItem> remove(Long accountId);

    public void removeById(Long id);

    public void updateCartItems(Long accountId, List<CartItem> cartItems);

    List<Product> getTopProductsAcrossAccounts(int limit);
}
