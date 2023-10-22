package com.example.demo.service;

import com.example.demo.service.dto.cart.CartItemDTO;
import com.example.demo.service.dto.product.ProductDTO;
import com.example.demo.service.util.localStorageUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private List<CartItemDTO> cartItems = new ArrayList<>();

    public void addToCart(ProductDTO product, int qty) {
        // Get the cart items from localStorage.
        List<CartItemDTO> cartItems = localStorageUtil.readFromLocalStorage();

        // Find the cart item with the specified ID.
        CartItemDTO existingCartItem = cartItems.stream()
                .filter(item -> item.getProducts().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        // Check if the product is already in the cart.
        if (existingCartItem != null) {
            // Update the quantity of the product.
            existingCartItem.setQty(qty);
        } else {
            // Add the product to the cart.
            CartItemDTO cartItem = new CartItemDTO();
            cartItem.setProducts(product);
            cartItem.setQty(qty);
            cartItems.add(cartItem);
        }

        // Save the cart items to localStorage.
        localStorageUtil.saveToLocalStorage(cartItems);
    }

    public void removeFromCart(String id) {
        cartItems.removeIf(item -> item.getProducts().getId().equals(id));
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }
}
