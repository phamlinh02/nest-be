package com.example.demo.web.rest;

import com.example.demo.service.CartService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.cart.CartItemDTO;
import com.example.demo.service.dto.product.ProductDTO;
import com.example.demo.service.util.localStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/nest/cart")
public class CartResource {
    @Autowired
    private CartService cartService;
     public CartResource(CartService cartService){
         this.cartService = cartService;
     }

    // GET /api/nest/cart/items
    @GetMapping("/items")
    public ResponseDTO getCartItems() {
        List<CartItemDTO> cartItems = localStorageUtil.readFromLocalStorage();
        return ResponseDTO.success(cartItems);
    }

    // POST /api/nest/cart/add
    @PostMapping("/add")
    public ResponseDTO addToCart(@RequestBody CartItemDTO cartItem) {
        ProductDTO product = cartItem.getProducts();
        int quantity = cartItem.getQty();
        cartService.addToCart(product, quantity);
        // Save cart items to localStorage
        List<CartItemDTO> cartItems = cartService.getCartItems();
        localStorageUtil.saveToLocalStorage(cartItems);
        return ResponseDTO.success();
    }

    // DELETE /api/nest/cart/remove/{id}
    @DeleteMapping("/remove/{id}")
    public ResponseDTO removeFromCart(@PathVariable("id") String id) {
        // Remove the cart item from the cart service.
        cartService.removeFromCart(id);

        // Update the cart items in localStorage.
        List<CartItemDTO> cartItems = cartService.getCartItems();
        localStorageUtil.saveToLocalStorage(cartItems);
        return ResponseDTO.success();
    }

    // DELETE /api/nest/cart/remove
    @DeleteMapping("/remove")
    public ResponseDTO removeAllCart() {
        // Get the cart items.
        List<CartItemDTO> cartItems = cartService.getCartItems();
        // Clear the cart items.
        cartItems.clear();
        // Save the cart items to localStorage.
        localStorageUtil.saveToLocalStorage(cartItems);
        return ResponseDTO.success();
    }
}