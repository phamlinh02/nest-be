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
    private Date fromDate;
    private Date toDate;
    private String description;
    @Enumerated(EnumType.STRING)
    private Constant.BILL_STATUS status;
    private List<BillDetailDTO> orderDetails;
    private String reasonDeny;
    private Long countOrder;
    private BigDecimal sumPriceBill;
    private String email;
    private String username;
    private String fullName;
    private String paymentId;


//    getAllOrder
    BillDTO(Long id, Date orderDate, String status, String description, String reasonDeny,
            Long countOrder, BigDecimal sumPriceBill, String email, String username, String fullName, String paymentId){
        this.id = id;
        this.orderDate = orderDate;
        this.status = Constant.BILL_STATUS.valueOf(status);
        this.description = description;
        this.reasonDeny = reasonDeny;
        this.countOrder = countOrder;
        this.sumPriceBill = sumPriceBill;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.paymentId = paymentId;
    }
}
