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
public class InvestmentAccountPojo extends AccountDetails {
    private Long accountId;
    private String investmentType;
    private LocalDate purchaseDate;
    private LocalDate maturityDate;
    private BigDecimal amountInvested;
    private BigDecimal expectedReturn;
    private BigDecimal currentValue;
}
