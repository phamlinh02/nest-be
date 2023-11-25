package com.example.demo.web.rest;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.CartItem;
import com.example.demo.domain.Product;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.cart.CartItemDTO;
import com.example.demo.service.dto.product.ProductDTO;
import com.example.demo.service.util.CartItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseDTO getAllCartByIdAccount(@RequestParam Long accountId) {
        try {
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
    public ResponseDTO removeAllItem(@RequestParam Long accountId) {
        try {
            cartItemSevice.remove(accountId);
            return ResponseDTO.success(accountId);
        } catch (Exception e) {
            return ResponseDTO.error();
        }
    }

    @DeleteMapping("removeid")
    public ResponseDTO removeById(@RequestParam Long id) {
        try {
            cartItemSevice.removeById(id);
            return ResponseDTO.success(id);
        } catch (Exception e) {
            return ResponseDTO.error();
        }
    }

    @PutMapping("/update")
    public ResponseDTO updateCartItems(@RequestParam Long accountId, @RequestBody List<CartItemDTO> cartItemsDTO) {
        try {
            // Chuyển đổi danh sách CartItemDTO thành danh sách CartItem
            List<CartItem> cartItems = new ArrayList<>();
            for (CartItemDTO cartItemDTO : cartItemsDTO) {
                CartItem cartItem = new CartItem();
                cartItem.setId(cartItemDTO.getId());
                cartItem.setAccountId(accountId);
                cartItem.setQuantity(cartItemDTO.getQuantity());
                cartItems.add(cartItem);
            }
            // Cập nhật các CartItem trong giỏ hàng
            cartItemSevice.updateCartItems(accountId, cartItems);
            // Trả về thông báo thành công
            return ResponseDTO.success("Cart items updated successfully");
        } catch (Exception e) {
            // Trả về thông báo lỗi nếu xảy ra lỗi
            return ResponseDTO.error();
        }
    }

    @GetMapping("/list/top")
    public ResponseDTO getTop10ProductPopular(){
        try {
            List<Product> cartItems = cartItemSevice.getTopProductsAcrossAccounts(10);
            return ResponseDTO.success(cartItems);
        } catch (Exception e) {
            return ResponseDTO.error();
        }
    }
}


