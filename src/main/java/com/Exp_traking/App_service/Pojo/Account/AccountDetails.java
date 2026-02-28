package com.Exp_traking.App_service.Pojo.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails {
    private Long id;
    private String userId;
    private String type;
    private String displayName;
    private BigDecimal balance;
    private String currency;
    private String status;
    private LocalDate createdAt;
    private AccountExtraData additionalData;
}

