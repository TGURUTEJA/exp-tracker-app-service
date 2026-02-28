package com.Exp_traking.App_service.Services;



import com.Exp_traking.App_service.ApiHelper.AuthAPIHelper;
import com.Exp_traking.App_service.Apicalls.Util;
import com.Exp_traking.App_service.Pojo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.Exp_traking.App_service.FunctionalInterfaces.EXpService;

import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpServiceImp implements EXpService {

    final private ExpServiceHelper expServiceHelper;
    final private AuthAPIHelper authAPIHelper;
    final private Util util;

//    public Mono<UserResponse> getData(ServerWebExchange exchange,UserRequest userRequest) {
//        // Logic to fetch data based on userCred
//        Mono<UserResponse> responce =  userCredAPIHandelar.customApicall("/userCreds/username/"+userRequest.getUsername(), UserResponse.class, "GET", userRequest);
//        return responce;
//    }
    public Mono<ApiResponse<UserResponse>> getData1(String ID) {
        // Logic to fetch data based on userCred
        System.out.println("Received /getData request"+ ID);
        UserResponse mockResponse = new UserResponse();
        UserData data = new UserData();
        data.setUserId(1);
        data.setUserName("mockUser");
        data.setEmail("mock email");
        mockResponse.setError(false);
        mockResponse.setMessage("Mock data retrieved successfully");
        List<UserData> list = List.of(data);
        mockResponse.setUserData(list);
        return util.buildApiSuccessResponse("Data fetched successfully", mockResponse);
    }
    public Mono<ApiResponse<RegisterUserDBResponse>> register(RegistrationRequest request){
        RegistrationResponse response = expServiceHelper.registrationRequestCheck(request);
        if(response.isError()){
            return util.buildApiErrorResponse("Invalid Request Body", null);
        }
        return authAPIHelper.registerUserAuth(request).flatMap(res->{
            if(res.isError()){
                return util.buildApiErrorResponse(res.getMessage(), null);
            }
            return authAPIHelper.registerUserDataBase(request,res).onErrorResume(err-> util.buildApiErrorResponse("Error in registering user in database", null));
        });
    }

    
}
