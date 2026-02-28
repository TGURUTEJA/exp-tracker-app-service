package com.Exp_traking.App_service.Pojo.Account;

import lombok.Data;

import java.util.List;

@Data
public class AccountResponse {
    private AccountSummary accountSummary;
    private List<AccountDetails> accountDetailsList;
}
