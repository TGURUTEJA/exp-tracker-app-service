package com.Exp_traking.App_service.ApiHelper;

import com.Exp_traking.App_service.Apicalls.CustomAPIHandler;
import com.Exp_traking.App_service.Constants.GraphqlQuries;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.Transaction.TransactionDetails;
import com.Exp_traking.App_service.Pojo.Transaction.TransactionRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TransactionAPIHelper {
    private final CustomAPIHandler customAPIHandler;

    public Mono<List<TransactionDetails>> getTransactionsByUserId(String userId) {
        return customAPIHandler.GraphQlApiCall(
                GraphqlQuries.Get_Transactions_By_UserID,
                Map.of("userId", userId),
                new TypeReference<List<TransactionDetails>>() {},
                "getTransactionsByUserId"
        );
    }

    public Mono<List<TransactionDetails>> getTransactionsByUserId(
            String userId,
            String startDate,
            String endDate,
            Double accountId,
            Integer categoryId
    ) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", userId);
        variables.put("newestFirst", true);
        addIfPresent(variables, "startDate", startDate);
        addIfPresent(variables, "endDate", endDate);
        addIfPresent(variables, "accountId", accountId);
        addIfPresent(variables, "categoryId", categoryId);
        return customAPIHandler.GraphQlApiCall(
                GraphqlQuries.Get_Filtered_Transactions_By_UserID,
                variables,
                new TypeReference<List<TransactionDetails>>() {},
                "getTransactionsByUserId"
        );
    }

    public Mono<ApiResponse<TransactionDetails>> createTransaction(
            String userId,
            TransactionRequest request
    ) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", userId);
        variables.put("input", request);
        return customAPIHandler.GraphQlApiCall(
                GraphqlQuries.Create_Transaction,
                variables,
                new TypeReference<ApiResponse<TransactionDetails>>() {},
                "createTransaction"
        );
    }

    public Mono<ApiResponse<TransactionDetails>> updateTransaction(
            Integer id,
            String userId,
            TransactionRequest request
    ) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("userId", userId);
        variables.put("input", request);
        return customAPIHandler.GraphQlApiCall(
                GraphqlQuries.Update_Transaction,
                variables,
                new TypeReference<ApiResponse<TransactionDetails>>() {},
                "updateTransaction"
        );
    }

    public Mono<ApiResponse<Boolean>> deleteTransaction(Integer id, String userId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("userId", userId);
        return customAPIHandler.GraphQlApiCall(
                GraphqlQuries.Delete_Transaction,
                variables,
                new TypeReference<ApiResponse<Boolean>>() {},
                "deleteTransaction"
        );
    }

    private void addIfPresent(Map<String, Object> variables, String key, Object value) {
        if (value != null) {
            variables.put(key, value);
        }
    }
}
