package com.example.demo.service.util.serviceImpl;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.CartItem;
import com.example.demo.domain.Product;
import com.example.demo.repository.ICartItemRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.util.CartItemService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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



//    @Override
//    public CartItem addToCart(CartItem item) {
//        try {
//            Long productId = item.getProductId().getId(); // Lấy productId từ đối tượng Product
//            Product product = productService.findById(productId); // Lấy thông tin sản phẩm dựa trên productId
//            if (product == null) {
//                throw new NotFoundException("Product not found");
//            }
//
//            item.setProductId(product); // Gán thông tin sản phẩm cho trường productId trong CartItem
//            return cartItemReponse.save(item); // Lưu mục giỏ hàng mới
//        } catch (Exception e) {
//            throw new ServiceException("Error adding item to cart", e);
//        }
//    }

    @Override
    public CartItem addToCart(CartItem item) {
        try {
            Long productId = item.getProductId().getId(); // Lấy productId từ đối tượng Product
            Product product = productService.findById(productId); // Lấy thông tin sản phẩm dựa trên productId
            if (product == null) {
                throw new NotFoundException("Product not found");
            }

            item.setProductId(product); // Gán thông tin sản phẩm cho trường productId trong CartItem
            CartItem existingCartItem = cartItemReponse.findByAccountIdAndProductId(item.getAccountId(),item.getProductId());
            if (existingCartItem != null) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1L);
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
    public CartItem updateCart(Long id, Long quantity) {
        CartItem cartItem = cartItemReponse.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found product in Cart"));
        cartItem.setQuantity(quantity);
        cartItemReponse.save(cartItem);
        return cartItem;
    }
}

