package com.Exp_traking.App_service.Services;


import com.Exp_traking.App_service.ApiHelper.AuthAPIHelper;
import com.Exp_traking.App_service.ApiHelper.UserAPIHelper;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.RegisterUserDBResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAPIHelper userAPIHelper;

    public Mono<ApiResponse<RegisterUserDBResponse>> getUserDetailsByID(String userID) {
        return userAPIHelper.getUserDetails(userID);
    }

}
