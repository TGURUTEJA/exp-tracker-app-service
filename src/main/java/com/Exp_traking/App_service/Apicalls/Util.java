package com.Exp_traking.App_service.Apicalls;

import com.Exp_traking.App_service.Pojo.ApiResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class Util {
    public<T> Mono<ApiResponse<T>> buildApiErrorResponse(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setError(true);
        response.setMessage(message);
        response.setData(data);
        return Mono.just(response);
    }
    public <T> Mono<ApiResponse<T>> buildApiSuccessResponse(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setError(false);
        response.setMessage(message);
        response.setData(data);
        return Mono.just(response);
    }
}
