package com.example.demo.service.util.serviceImpl;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.CartItem;
import com.example.demo.domain.Product;
import com.example.demo.repository.ICartItemRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.dto.product.ProductDTO;
import com.example.demo.service.util.CartItemService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartItemImpl implements CartItemService {
    @Autowired
    ICartItemRepository cartItemReponse;
    @Autowired
    ProductService productService;

    @Override
    public List<CartItem> findByIdAccount(Long accountId) {
        return cartItemReponse.findByIdAccount(accountId);
    }

    @Override
    public CartItem addToCart(CartItem item) {
        try {
            Long productId = item.getProductId().getId(); // Lấy productId từ đối tượng Product
            Product product = productService.findById(productId); // Lấy thông tin sản phẩm dựa trên productId
            if (product == null) {
                throw new NotFoundException("Product not found");
            }

            item.setProductId(product); // Gán thông tin sản phẩm cho trường productId trong CartItem
            CartItem existingCartItem = cartItemReponse.findByAccountIdAndProductId(item.getAccountId(), item.getProductId());
            if (existingCartItem != null) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + item.getQuantity());
                cartItemReponse.save(existingCartItem);
                return existingCartItem;
            } else {
                return cartItemReponse.save(item);
            }
        } catch (Exception e) {
            throw new ServiceException("Error adding item to cart", e);
        }
    }

    @Override
    public List<CartItem> remove(Long accountId) {
        List<CartItem> itemsToRemove = cartItemReponse.findByIdAccount(accountId);
        cartItemReponse.deleteAll(itemsToRemove);
        return itemsToRemove;
    }

    @Override
    public void removeById(Long id) {
        cartItemReponse.deleteById(id);
    }

    @Override
    public void updateCartItems(Long accountId, List<CartItem> cartItems) {
        List<CartItem> existingCartItems = cartItemReponse.findByIdAccount(accountId);
        for (CartItem existingItem : existingCartItems) {
            // Tìm cart item mới trong danh sách cart items được cung cấp
            CartItem updatedItem = cartItems.stream()
                    .filter(item -> item.getId().equals(existingItem.getId()))
                    .findFirst()
                    .orElse(null);
            if (updatedItem != null) {
                // Cập nhật số lượng mới
                existingItem.setQuantity(updatedItem.getQuantity());
            }
        }
        // Lưu lại các thay đổi trong giỏ hàng
        cartItemReponse.saveAll(existingCartItems);
    }

    @Override
    public List<Product> getTopProductsAcrossAccounts(int limit) {
        List<CartItem> allCartItems = cartItemReponse.findAll();

        // Group cart items by product and sum the quantities
        Map<Product, Long> productQuantities = allCartItems.stream()
                .collect(Collectors.groupingBy(CartItem::getProductId,
                        Collectors.summingLong(CartItem::getQuantity)));

        // Sort products by total quantity in descending order
        List<Product> sortedProducts = productQuantities.entrySet().stream()
                .sorted(Map.Entry.<Product, Long>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return sortedProducts;
    }
}



