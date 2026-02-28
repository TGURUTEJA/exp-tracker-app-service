package com.Exp_traking.App_service.ApiHelper;

import com.Exp_traking.App_service.Apicalls.CustomAPIHandler;
import com.Exp_traking.App_service.Constants.GraphqlQuries;
import com.Exp_traking.App_service.Pojo.Account.AccountDetails;
import com.Exp_traking.App_service.Pojo.Account.AccountDetailsList;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountAPIHelper {
    private final CustomAPIHandler customAPIHandler;


    public Mono<ApiResponse<List<AccountDetails>>> GetAccountDetailsByUserID(String userID) {
        HashMap<String , String> params = new HashMap<>();
        params.put("id", userID);
        return customAPIHandler.GraphQlCustomApiCall(GraphqlQuries.Get_Accouts_By_UserID, params , new TypeReference<List<AccountDetails>>() {} ,"getAccountsByUserId" );
    }
}
