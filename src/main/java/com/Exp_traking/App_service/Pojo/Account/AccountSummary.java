package com.Exp_traking.App_service.Pojo.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class AccountSummary {
    private BigDecimal NetWorth;
    private BigDecimal TotalBankBalance;
    private BigDecimal TotalCreditCardBalance;
    private BigDecimal TotalInvestments;
    private BigDecimal TotalLoans;
    private BigDecimal TotalCash;
    private BigDecimal TotalFriendCredits;
    private BigDecimal TotalFriendDebits;
}
