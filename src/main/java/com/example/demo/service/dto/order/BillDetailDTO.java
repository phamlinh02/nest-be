package com.example.demo.service.dto.order;

import com.example.demo.service.dto.product.ProductDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillDetailDTO {

    private Long id;
    private Long orderId;
    private ProductDTO productDTO;
    private Integer quantity;
    private BigDecimal price;
    private Long productId;
}
