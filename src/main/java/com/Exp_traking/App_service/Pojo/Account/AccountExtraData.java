package com.Exp_traking.App_service.Pojo.Account;


import lombok.Data;

@Data
public class AccountExtraData {
    private CreditCardPojo creditCard;
    private EMIAccountPojo emi;
    private InvestmentAccountPojo investment;
    private FriendAccountPojo friend;
}
