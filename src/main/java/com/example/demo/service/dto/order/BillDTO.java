package com.example.demo.service.dto.order;

import com.example.demo.config.Constant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillDTO {

    private Long id;
    private Long accountId;
    private Long staffId;
    private Date orderDate;
    private String description;
    @Enumerated(EnumType.STRING)
    private Constant.BILL_STATUS status;
    private List<BillDetailDTO> orderDetails;
    private String reasonDeny;
    private Long countOrder;
    private BigDecimal sumPriceBill;


//    getAllOrder
    BillDTO(Long id, Date orderDate, String status, String description, String reasonDeny, Long countOrder, BigDecimal sumPriceBill){
        this.id = id;
        this.orderDate = orderDate;
        this.status = Constant.BILL_STATUS.valueOf(status);
        this.description = description;
        this.reasonDeny = reasonDeny;
        this.countOrder = countOrder;
        this.sumPriceBill = sumPriceBill;
    }
}
