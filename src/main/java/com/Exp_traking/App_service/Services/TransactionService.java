package com.Exp_traking.App_service.Services;

import com.Exp_traking.App_service.ApiHelper.TransactionAPIHelper;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.Transaction.TransactionDetails;
import com.Exp_traking.App_service.Pojo.Transaction.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionAPIHelper transactionAPIHelper;

    public Mono<List<TransactionDetails>> getTransactionsByUserId(String userId) {
        return transactionAPIHelper.getTransactionsByUserId(userId);
    }

    public Mono<List<TransactionDetails>> getTransactionsByUserId(
            String userId,
            String startDate,
            String endDate,
            Double accountId,
            Integer categoryId
    ) {
        return transactionAPIHelper.getTransactionsByUserId(
                userId, startDate, endDate, accountId, categoryId
        );
    }

    public Mono<ApiResponse<TransactionDetails>> createTransaction(
            String userId,
            TransactionRequest request
    ) {
        return transactionAPIHelper.createTransaction(userId, request);
    }

    public Mono<ApiResponse<TransactionDetails>> updateTransaction(
            Integer id,
            String userId,
            TransactionRequest request
    ) {
        return transactionAPIHelper.updateTransaction(id, userId, request);
    }

    public Mono<ApiResponse<Boolean>> deleteTransaction(Integer id, String userId) {
        return transactionAPIHelper.deleteTransaction(id, userId);
    }
}
