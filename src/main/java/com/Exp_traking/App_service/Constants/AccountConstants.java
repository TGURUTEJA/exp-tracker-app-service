package com.Exp_traking.App_service.Constants;

import lombok.Data;

import java.util.List;

@Data
public class AccountConstants {
    public static final String ACCOUNT_TYPE_BANK = "BANK";
    public static final String ACCOUNT_TYPE_CREDIT_CARD = "CREDIT_CARD";
    public static final String ACCOUNT_TYPE_CASH = "CASH";
    public static final String ACCOUNT_TYPE_LOAN = "LOAN";
    public static final String ACCOUNT_TYPE_INVESTMENT = "INVESTMENT";
    public static final String ACCOUNT_TYPE_FRIEND = "FRIEND";
    public static final List<String> Account_Types = List.of(
            ACCOUNT_TYPE_BANK,
            ACCOUNT_TYPE_CREDIT_CARD,
            ACCOUNT_TYPE_CASH,
            ACCOUNT_TYPE_LOAN,
            ACCOUNT_TYPE_INVESTMENT,
            ACCOUNT_TYPE_FRIEND
    );
}
