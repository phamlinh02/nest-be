package com.example.demo.service.util.impl;

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

    @Override
    public CartItem findByAccountIdAndProductId(Long accountId, Long productId) {
        return cartItemReponse.findByAccountIdAndProductId(accountId, productId);
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
            return cartItemReponse.save(item); // Lưu mục giỏ hàng mới
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

}


//    @Override
//    public CartItem addToCart(CartItem item) {
//        try {
//            Long accountId = item.getAccountId();
//            Long productId = item.getProductId().getId();
//
//            // Kiểm tra xem có mục giỏ hàng nào có cùng accountId và productId không
//            CartItem existingCartItem = reponse.findByAccountIdAndProductId(accountId, productId);
//
//            if (existingCartItem != null) {
//                // Nếu tồn tại, chỉ cần tăng quantity thêm 1
//                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
//                return reponse.save(existingCartItem);
//            } else {
//                // Ngược lại, tạo mới một mục giỏ hàng với quantity mặc định là 1
//                Product product = productService.findById(productId); // Lấy thông tin sản phẩm dựa trên productId
//                if (product == null) {
//                    throw new NotFoundException("Product not found");
//                }
//
//                item.setProductId(product); // Gán thông tin sản phẩm cho trường productId trong CartItem
//                return reponse.save(item); // Lưu mục giỏ hàng mới
//            }
//        } catch (Exception e) {
//            throw new ServiceException("Error adding item to cart", e);
//        }
//    }
