package com.Exp_traking.App_service.Controller;

import com.Exp_traking.App_service.Pojo.Transaction.TransactionDetails;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.Transaction.TransactionRequest;
import com.Exp_traking.App_service.Services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/App/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public Mono<List<TransactionDetails>> getTransactions(
            ServerWebExchange exchange,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Double accountId,
            @RequestParam(required = false) Integer categoryId
    ) {
        return transactionService.getTransactionsByUserId(
                requireUserId(exchange), startDate, endDate, accountId, categoryId
        );
    }

    @GetMapping("/history")
    public Mono<List<TransactionDetails>> getUserTransactionHistory(ServerWebExchange exchange) {
        return transactionService.getTransactionsByUserId(requireUserId(exchange));
    }

    @PostMapping
    public Mono<ApiResponse<TransactionDetails>> createTransaction(
            ServerWebExchange exchange,
            @Valid @RequestBody TransactionRequest request
    ) {
        return transactionService.createTransaction(requireUserId(exchange), request);
    }

    @PutMapping("/{id}")
    public Mono<ApiResponse<TransactionDetails>> updateTransaction(
            ServerWebExchange exchange,
            @PathVariable Integer id,
            @Valid @RequestBody TransactionRequest request
    ) {
        return transactionService.updateTransaction(id, requireUserId(exchange), request);
    }

    @DeleteMapping("/{id}")
    public Mono<ApiResponse<Boolean>> deleteTransaction(
            ServerWebExchange exchange,
            @PathVariable Integer id
    ) {
        return transactionService.deleteTransaction(id, requireUserId(exchange));
    }

    private String requireUserId(ServerWebExchange exchange) {
        String userId = exchange.getAttribute("ID");
        if (userId == null || userId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }
        return userId;
    }
}
