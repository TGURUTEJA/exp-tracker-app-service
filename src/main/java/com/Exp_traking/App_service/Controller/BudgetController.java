package com.Exp_traking.App_service.Controller;

import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.Budget.BudgetDetails;
import com.Exp_traking.App_service.Pojo.Budget.BudgetRequest;
import com.Exp_traking.App_service.Services.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/App/budget")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @GetMapping
    public Mono<List<BudgetDetails>> getBudgets(
            ServerWebExchange exchange,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        String userId = requireUserId(exchange);
        if ((year == null) != (month == null)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "year and month must be provided together"
            );
        }
        return year == null
                ? budgetService.getBudgets(userId)
                : budgetService.getBudgets(userId, year, month);
    }

    @PostMapping
    public Mono<ApiResponse<BudgetDetails>> createBudget(
            ServerWebExchange exchange,
            @Valid @RequestBody BudgetRequest request
    ) {
        return budgetService.createBudget(requireUserId(exchange), request);
    }

    @PutMapping("/{id}")
    public Mono<ApiResponse<BudgetDetails>> updateBudget(
            ServerWebExchange exchange,
            @PathVariable String id,
            @Valid @RequestBody BudgetRequest request
    ) {
        return budgetService.updateBudget(id, requireUserId(exchange), request);
    }

    @DeleteMapping("/{id}")
    public Mono<ApiResponse<Boolean>> deleteBudget(
            ServerWebExchange exchange,
            @PathVariable String id
    ) {
        return budgetService.deleteBudget(id, requireUserId(exchange));
    }

    private String requireUserId(ServerWebExchange exchange) {
        String userId = exchange.getAttribute("ID");
        if (userId == null || userId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }
        return userId;
    }
}
