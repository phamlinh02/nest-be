package com.example.demo.service.dto.cart;

import com.example.demo.service.dto.account.AccountDTO;
import com.example.demo.service.dto.product.ProductDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemDTO {
    private long id;
    private ProductDTO products;
    private AccountDTO account;
}