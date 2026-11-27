package com.Exp_traking.App_service.ApiHelper;

import com.Exp_traking.App_service.Apicalls.CustomAPIHandler;
import com.Exp_traking.App_service.Constants.GraphqlQuries;
import com.Exp_traking.App_service.Pojo.Budget.BudgetDetails;
import com.Exp_traking.App_service.Pojo.Budget.BudgetRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BudgetAPIHelper {
    private final CustomAPIHandler customAPIHandler;

    public Mono<List<BudgetDetails>> getBudgetsByUserId(String userId) {
        return customAPIHandler.GraphQlApiCall(
                GraphqlQuries.Get_Budgets_By_UserID,
                Map.of("userId", userId),
                new TypeReference<List<BudgetDetails>>() {},
                "getBudgetsByUserId"
        );
    }

    public Mono<List<BudgetDetails>> getBudgetsByUserIdAndPeriod(
            String userId,
            Integer startYear,
            Integer startMonth,
            Integer endYear,
            Integer endMonth
    ) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", userId);
        variables.put("startYear", startYear);
        variables.put("startMonth", startMonth);
        variables.put("endYear", endYear);
        variables.put("endMonth", endMonth);
        return customAPIHandler.GraphQlApiCall(
                GraphqlQuries.Get_Budgets_By_UserID_And_Period,
                variables,
                new TypeReference<List<BudgetDetails>>() {},
                "getBudgetsByUserIdAndDateRange"
        );
    }

    public Mono<BudgetDetails> createBudget(String userId, BudgetRequest request) {
        return customAPIHandler.GraphQlApiCall(
                GraphqlQuries.Create_Budget,
                toVariables(userId, request),
                new TypeReference<BudgetDetails>() {},
                "createBudget"
        );
    }

    public Mono<BudgetDetails> updateBudget(String id, String userId, BudgetRequest request) {
        Map<String, Object> variables = toVariables(userId, request);
        variables.put("id", id);
        return customAPIHandler.GraphQlApiCall(
                GraphqlQuries.Update_Budget,
                variables,
                new TypeReference<BudgetDetails>() {},
                "updateBudget"
        );
    }

    public Mono<Boolean> deleteBudget(String id, String userId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("userId", userId);
        return customAPIHandler.GraphQlApiCall(
                GraphqlQuries.Delete_Budget,
                variables,
                new TypeReference<Boolean>() {},
                "deleteBudget"
        );
    }

    private Map<String, Object> toVariables(String userId, BudgetRequest request) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", userId);
        variables.put("categoryId", request.getCategoryId());
        variables.put("year", request.getYear());
        variables.put("month", request.getMonth());
        variables.put("amount", request.getAmount());
        variables.put("isActive", request.getIsActive());
        variables.put("recurrence", request.getRecurrence());
        return variables;
    }
}
