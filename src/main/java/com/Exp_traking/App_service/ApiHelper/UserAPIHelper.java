package com.Exp_traking.App_service.ApiHelper;


import com.Exp_traking.App_service.Apicalls.CustomAPIHandler;
import com.Exp_traking.App_service.Constants.GraphqlQuries;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.RegisterUserDBResponse;
import com.Exp_traking.App_service.Pojo.UserProfileUpdateRequest;
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

    public Mono<ApiResponse<RegisterUserDBResponse>> updateUserDetails(
            String id,
            UserProfileUpdateRequest request
    ) {
        HashMap<String, Object> input = new HashMap<>();
        input.put("firstName", request.getFirstName());
        input.put("lastName", request.getLastName());
        input.put("dob", request.getDob());

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("input", input);
        return customAPIHandler.GraphQlApiCall(
                GraphqlQuries.Update_User,
                variables,
                new TypeReference<ApiResponse<RegisterUserDBResponse>>() {},
                "updateUser"
        );
    }

}
