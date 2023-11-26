package com.example.demo.service.dto.order;

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
public class StatisticsBillDTO {
    private List<BillDTO> totalBill;
    private List<BillDTO> newBill;
    private List<BillDTO> cancelBill;
    private List<BillDTO> completeBill;
}
