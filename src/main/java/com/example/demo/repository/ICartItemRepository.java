package com.example.demo.repository;

import com.example.demo.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT o FROM CartItem o WHERE o.accountId=?1")
    List<CartItem> findByIdAccount(Long id);
    @Query("SELECT o FROM CartItem o WHERE o.accountId=?1 AND o.productId=?1")
    CartItem findByAccountIdAndProductId(Long accountId,Long productId);

}
