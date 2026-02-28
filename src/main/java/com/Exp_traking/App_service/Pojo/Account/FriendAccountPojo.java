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
public class FriendAccountPojo extends AccountDetails {
    private Long accountId;
    private String friendName;
    private String contactInfo;
    private BigDecimal outstandingAmount;
    private LocalDate loanStartDate;
    private LocalDate loanDueDate;
}
