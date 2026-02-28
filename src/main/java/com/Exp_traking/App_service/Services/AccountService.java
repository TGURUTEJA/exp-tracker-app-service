package com.Exp_traking.App_service.Services;

import com.Exp_traking.App_service.ApiHelper.AccountAPIHelper;
import com.Exp_traking.App_service.Apicalls.Util;
import com.Exp_traking.App_service.Constants.AccountConstants;
import com.Exp_traking.App_service.Pojo.Account.AccountDetails;
import com.Exp_traking.App_service.Pojo.Account.AccountResponse;
import com.Exp_traking.App_service.Pojo.Account.AccountSummary;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountAPIHelper accountAPIHelper;
    private final Util util;

    public Mono<ApiResponse<AccountResponse>> getAccountDetailsByUserId(String UserId) {
        AccountResponse accountResponse = new AccountResponse();
        return accountAPIHelper.GetAccountDetailsByUserID(UserId).flatMap(apiResponse -> {
           if(apiResponse.isError()){
               return util.buildApiErrorResponse(apiResponse.getMessage(),accountResponse);
           }
           List<AccountDetails> accountDetailsList = apiResponse.getData();
          accountResponse.setAccountDetailsList(accountDetailsList);
            BigDecimal ZERO = BigDecimal.ZERO;

            AccountSummary accountSummary = AccountSummary.builder()
                    .NetWorth(ZERO)
                    .TotalBankBalance(ZERO)
                    .TotalCreditCardBalance(ZERO)
                    .TotalInvestments(ZERO)
                    .TotalLoans(ZERO)
                    .TotalCash(ZERO)
                    .TotalFriendCredits(ZERO)
                    .TotalFriendDebits(ZERO)
                    .build();
          if(!accountDetailsList.isEmpty()){
              for (AccountDetails account : accountDetailsList) {
                      if(AccountConstants.Account_Types.contains(account.getType())){
                      BigDecimal balance = Optional.ofNullable(account.getBalance()).orElse(ZERO);
                       switch (account.getType()) {
                           case AccountConstants.ACCOUNT_TYPE_BANK -> accountSummary.setTotalBankBalance(
                                   accountSummary.getTotalBankBalance().add(balance)
                           );
                           case AccountConstants.ACCOUNT_TYPE_CASH -> accountSummary.setTotalCash(
                                   accountSummary.getTotalCash().add(balance)
                           );
                           case AccountConstants.ACCOUNT_TYPE_FRIEND -> {
                               if(balance.signum() > 0){
                                   accountSummary.setTotalFriendCredits(
                                           accountSummary.getTotalFriendCredits().add(balance)
                                   );
                               } else {
                                   accountSummary.setTotalFriendDebits(
                                           accountSummary.getTotalFriendDebits().add(balance.abs())
                                   );
                               }
                           }
                           case AccountConstants.ACCOUNT_TYPE_INVESTMENT -> accountSummary.setTotalInvestments(
                                   accountSummary.getTotalInvestments().add(balance)
                           );
                           case AccountConstants.ACCOUNT_TYPE_LOAN -> accountSummary.setTotalLoans(
                                   accountSummary.getTotalLoans().add(balance)
                           );
                       }
                      }
                  }
              accountSummary.setNetWorth(
                  accountSummary.getTotalBankBalance()
                  .add(accountSummary.getTotalCash())
                  .add(accountSummary.getTotalInvestments())
                  .add(accountSummary.getTotalFriendCredits())
                  .subtract(accountSummary.getTotalCreditCardBalance())
                  .subtract(accountSummary.getTotalLoans())
                  .subtract(accountSummary.getTotalFriendDebits())
              );

          }
          accountResponse.setAccountSummary(accountSummary);
          return util.buildApiSuccessResponse("Account details fetched successfully", accountResponse);
        });
    }
}
