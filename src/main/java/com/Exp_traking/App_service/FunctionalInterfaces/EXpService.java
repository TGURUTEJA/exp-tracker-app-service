package com.Exp_traking.App_service.FunctionalInterfaces;

import com.Exp_traking.App_service.Pojo.*;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public interface EXpService {
//    Mono<UserResponse> getData(ServerWebExchange exchange,UserRequest userRequest);
    Mono<ApiResponse<UserResponse>> getData1(String ID);

    Mono<ApiResponse<RegisterUserDBResponse>> register(RegistrationRequest request);
} 
