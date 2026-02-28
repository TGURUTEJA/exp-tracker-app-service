package com.Exp_traking.App_service.ApiHelper;

import com.Exp_traking.App_service.Apicalls.CustomAPIHandler;
import com.Exp_traking.App_service.Constants.GraphqlQuries;
import com.Exp_traking.App_service.Pojo.*;
import com.Exp_traking.App_service.Pojo.Account.AccountDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthAPIHelper {
    final private CustomAPIHandler customAPIHandler;
    public Mono<RegistrationResponse> registerUserAuth(RegistrationRequest request){
        AuthRegisterRequest authRegisterRequest=new AuthRegisterRequest();
        authRegisterRequest.setUserName(request.getUserName());
        authRegisterRequest.setPassword(request.getPassword());
        authRegisterRequest.setEmail(request.getEmail());
        return customAPIHandler.customApicall("/api/auth/register",RegistrationResponse.class,"POST",authRegisterRequest);
    }

    public Mono<ApiResponse<RegisterUserDBResponse>> registerUserDataBase(RegistrationRequest request, RegistrationResponse res) {
        UserGraphQLRequest data = new UserGraphQLRequest();
        data.setId(res.getID());
        data.setFirstName(request.getFirstName());
        data.setLastName(request.getLastName());
        data.setDob(request.getDOB());
        return customAPIHandler.GraphQlCustomApiCall(GraphqlQuries.Create_User, data , new TypeReference<RegisterUserDBResponse>() {} ,"createUser" );
    }

//    public CorePublisher<Object> deleteUserAuth(String id) {
//        return authServiceAPIHandler.customApicall("/api/auth/delete/"+id, Object.class,"DELETE",null);
//    }
}
