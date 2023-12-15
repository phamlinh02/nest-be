package com.example.demo.service.dto.order;

import com.example.demo.service.dto.account.AccountDTO;
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
public class TransactionDTO {

    private AccountDTO accountDTO;
    private TransactionDetails transactionDetails;
    private BillDTO billDTO;
}
