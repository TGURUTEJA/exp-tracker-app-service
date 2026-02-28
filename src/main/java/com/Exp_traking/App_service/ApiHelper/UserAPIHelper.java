package com.Exp_traking.App_service.ApiHelper;


import com.Exp_traking.App_service.Apicalls.CustomAPIHandler;
import com.Exp_traking.App_service.Constants.GraphqlQuries;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.RegisterUserDBResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class UserAPIHelper {

    private final CustomAPIHandler customAPIHandler;
    public Mono<ApiResponse<RegisterUserDBResponse>> getUserDetails(String ID) {
        HashMap<String , String> variables = new HashMap<>();
        variables.put("id", ID);
        return customAPIHandler.GraphQlCustomApiCall(GraphqlQuries.Get_User_By_ID, variables, new TypeReference<RegisterUserDBResponse>() {}, "getUserById");
    }

}
