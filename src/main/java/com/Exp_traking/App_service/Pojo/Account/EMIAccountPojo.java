package com.Exp_traking.App_service.Pojo.Account;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EMIAccountPojo extends AccountDetails{
    private Long accountId;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private Integer totalInstallments;
    private Integer paidInstallments;
    private BigDecimal emiAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate nextDueDate;
}
