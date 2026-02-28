package com.Exp_traking.App_service.Controller;


import com.Exp_traking.App_service.Pojo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

import com.Exp_traking.App_service.Filter.RequestFilter;
import com.Exp_traking.App_service.FunctionalInterfaces.EXpService;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/")// normalized base path
@RequiredArgsConstructor
public class ServiceController{

    private final EXpService eXpService;
    private final RequestFilter jwtFilter;

    @GetMapping("/status")
    public String status() {
        return "Service is running";
    }
//    @GetMapping("/getData")
//    public Mono<ResponseEntity<UserResponse>> getData(ServerWebExchange exchange) {
//        System.out.println("Received /getData request");
//        return jwtFilter.biFilterRequest(exchange, eXpService::getData);
//    }
     @GetMapping("/getData1")
     public Mono<ApiResponse<UserResponse>> getData1(ServerWebExchange exchange) {
         String user = exchange.getAttribute("ID");
        return eXpService.getData1(user);
         //return jwtFilter.biFilterRequest(exchange, (exchange1, userRequest) -> eXpService.getData1(userRequest));
     }

     @PostMapping(value = "/register")
     public Mono<ApiResponse<RegisterUserDBResponse>> register(@RequestBody RegistrationRequest request) {
         return eXpService.register(request);
     }
}
