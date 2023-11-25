package com.example.demo.service.dto.order;

import com.example.demo.service.dto.account.AccountDTO;
import com.example.demo.service.dto.product.ProductDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewBillDetail {
    private AccountDTO account;
    private List<BillDetailDTO> billDetails;
    private BillDTO bill;
}
