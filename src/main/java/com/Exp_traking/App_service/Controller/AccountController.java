package com.Exp_traking.App_service.Controller;


import com.Exp_traking.App_service.Pojo.Account.AccountDetails;
import com.Exp_traking.App_service.Pojo.Account.AccountDetailsList;
import com.Exp_traking.App_service.Pojo.Account.AccountResponse;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Services.AccountService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/App/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @RequestMapping("/test")
    public String testAccount() {
        return "Account service is up and running!";
    }

    @GetMapping("/Details_by_userId")
    public Mono<ApiResponse<AccountResponse>> accountDetailsByUserId(ServerWebExchange exchange) {
        String userId = exchange.getAttribute("ID");
        return accountService.getAccountDetailsByUserId(userId);
    }


}
