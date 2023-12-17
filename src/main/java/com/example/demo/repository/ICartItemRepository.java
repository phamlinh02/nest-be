package com.example.demo.repository;

import com.example.demo.domain.CartItem;
import com.example.demo.domain.Product;
import com.example.demo.service.dto.cart.CartItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT o FROM CartItem o WHERE o.accountId=?1 AND o.productId.isActive = TRUE")
    List<CartItem> findByIdAccount(Long id);

    @Query("SELECT o FROM CartItem o WHERE o.productId.isActive = TRUE")
    List<CartItem> finfAllIsActive();

    @Query("SELECT o FROM CartItem o WHERE o.accountId= ?1 AND o.productId = ?2")
    CartItem findByAccountIdAndProductId(Long accountId, Product productId);


}
