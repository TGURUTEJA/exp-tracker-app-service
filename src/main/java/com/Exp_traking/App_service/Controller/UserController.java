package com.Exp_traking.App_service.Controller;


import com.Exp_traking.App_service.Filter.RequestFilter;
import com.Exp_traking.App_service.Pojo.Account.AccountDetails;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.RegisterUserDBResponse;
import com.Exp_traking.App_service.Pojo.UserData;
import com.Exp_traking.App_service.Services.AccountService;
import com.Exp_traking.App_service.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/App/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    @GetMapping("/Details")
    public Mono<ApiResponse<RegisterUserDBResponse>> userDetails(ServerWebExchange exchange) {
        String ID = exchange.getAttribute("ID");
        return userService.getUserDetailsByID(ID);
    }

}
