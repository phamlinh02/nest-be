package com.example.demo.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.example.demo.service.dto.order.BillDTO;
import com.example.demo.service.dto.order.ViewBillDetail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BillDetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long productId;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal price;
}	
