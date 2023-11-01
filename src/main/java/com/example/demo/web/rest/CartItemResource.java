package com.example.demo.web.rest;

import com.example.demo.domain.CartItem;
import com.example.demo.domain.Product;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.cart.CartItemDTO;
import com.example.demo.service.util.CartItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nest/cart")
@CrossOrigin("*")
public class CartItemResource {
    @Autowired
    CartItemService cartItemSevice;

    public CartItemResource(CartItemService cartItemService) {
        this.cartItemSevice = cartItemService;
    }

    @GetMapping("/list")
    public ResponseDTO getAllCartByIdAccount(@RequestBody Map<String, Long> requestBody) {
        try {
            Long accountId = requestBody.get("accountId");
            List<CartItem> cartItems = cartItemSevice.findByIdAccount(accountId);
            return ResponseDTO.success(cartItems);
        } catch (Exception e) {
            return ResponseDTO.error();
        }
    }

    @PostMapping("/add")
    public ResponseDTO addToCart(@RequestBody CartItem item) {
        try {
            CartItem addCart = cartItemSevice.addToCart(item);
            return ResponseDTO.success(addCart);
        } catch (Exception e) {
            return ResponseDTO.error();
        }
    }

    @DeleteMapping("/remove")
    public ResponseDTO removeAllItem(@RequestBody Map<String, Long> requestBody) {
        try {
            Long accountId = requestBody.get("accountId");
            cartItemSevice.remove(accountId);
            return ResponseDTO.success(accountId);
        } catch (Exception e) {
            return ResponseDTO.error();
        }
    }

    @DeleteMapping("removeid")
    public ResponseDTO removeById(@RequestBody Map<String, Long> requestBody) {
        try {
            Long id = requestBody.get("id");
            cartItemSevice.removeById(id);
            return ResponseDTO.success(id);
        } catch (Exception e) {
            return ResponseDTO.error();
        }
    }

    @PutMapping("/update")
    public ResponseDTO updateQuantity(@RequestBody CartItemDTO request) {
        CartItem cartItem = cartItemSevice.updateCart(request.getId(), request.getQuantity());
        return ResponseDTO.success(cartItem);
    }
}

