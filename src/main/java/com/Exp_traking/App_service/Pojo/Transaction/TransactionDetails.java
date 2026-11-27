package com.Exp_traking.App_service.Pojo.Transaction;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransactionDetails {
    private Integer id;
    private String userId;
    private Double accountId;
    private Integer categoryId;
    private String description;
    private BigDecimal amount;
    private String debitCredit;
    private String created;
    private BigDecimal accountBalance;
}
