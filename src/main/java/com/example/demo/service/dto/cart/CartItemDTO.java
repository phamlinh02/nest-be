package com.example.demo.service.dto.cart;

import com.example.demo.service.dto.product.ProductDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemDTO {
    private ProductDTO products;
    private int qty;
}