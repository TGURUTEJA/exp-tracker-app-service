package com.Exp_traking.App_service.Services;

import com.Exp_traking.App_service.ApiHelper.BudgetAPIHelper;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.Budget.BudgetDetails;
import com.Exp_traking.App_service.Pojo.Budget.BudgetRequest;
import com.Exp_traking.App_service.Pojo.GraphQL.GraphQLException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetAPIHelper budgetAPIHelper;

    public Mono<List<BudgetDetails>> getBudgets(String userId) {
        return budgetAPIHelper.getBudgetsByUserId(userId);
    }

    public Mono<List<BudgetDetails>> getBudgets(String userId, Integer year, Integer month) {
        return budgetAPIHelper.getBudgetsByUserIdAndPeriod(userId, year, month, year, month);
    }

    public Mono<ApiResponse<BudgetDetails>> createBudget(String userId, BudgetRequest request) {
        return budgetAPIHelper.createBudget(userId, request)
                .map(budget -> response("Budget created successfully", false, budget))
                .onErrorResume(error -> Mono.just(errorResponse("Failed to create budget", error)));
    }

    public Mono<ApiResponse<BudgetDetails>> updateBudget(String id, String userId, BudgetRequest request) {
        return budgetAPIHelper.updateBudget(id, userId, request)
                .map(budget -> response("Budget updated successfully", false, budget))
                .onErrorResume(error -> Mono.just(errorResponse("Failed to update budget", error)));
    }

    public Mono<ApiResponse<Boolean>> deleteBudget(String id, String userId) {
        return budgetAPIHelper.deleteBudget(id, userId)
                .map(deleted -> response(
                        deleted ? "Budget deleted successfully" : "Budget could not be deleted",
                        !deleted,
                        deleted
                ))
                .onErrorResume(error -> Mono.just(errorResponse("Failed to delete budget", error)));
    }

    private <T> ApiResponse<T> response(String message, boolean error, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMessage(message);
        response.setError(error);
        response.setData(data);
        return response;
    }

    private <T> ApiResponse<T> errorResponse(String action, Throwable error) {
        String detail;
        if (error instanceof GraphQLException ex) {
            String message = ex.getClassification() != null ? ex.getClassification() : ex.getMessage();
            detail = action + " [" + ex.getCode() + "]: " + message;
        } else {
            String message = error.getMessage() != null ? error.getMessage() : error.getClass().getSimpleName();
            detail = action + ": " + message;
        }
        return response(detail, true, null);
    }
}
