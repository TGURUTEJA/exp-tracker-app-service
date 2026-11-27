package com.Exp_traking.App_service.ApiHelper;

import com.Exp_traking.App_service.Apicalls.CustomAPIHandler;
import com.Exp_traking.App_service.Constants.GraphqlQuries;
import com.Exp_traking.App_service.Pojo.Account.AccountDetails;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.Category.CategoryCreateRequest;
import com.Exp_traking.App_service.Pojo.Category.CategoryCreateResponse;
import com.Exp_traking.App_service.Pojo.Category.CategoryResponse;
import com.Exp_traking.App_service.Pojo.Category.CategoryUpdateRequest;
import com.Exp_traking.App_service.Pojo.Category.CategoryDeleteRequest;
import com.Exp_traking.App_service.Pojo.Category.CategoryDeleteResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CategoryAPIHelper {
    private final CustomAPIHandler customAPIHandler;

    public Mono<CategoryCreateResponse> cresteCategoty(CategoryCreateRequest request) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", request);
        return customAPIHandler.GraphQlApiCall(GraphqlQuries.Create_Category, variables , new TypeReference<CategoryCreateResponse>() {},"createCategory" );
    }

    public Mono<java.util.List<CategoryResponse>> getCategoriesByUserId(String userId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", userId);
        return customAPIHandler.GraphQlApiCall(GraphqlQuries.Get_Categories_By_UserID, variables, new TypeReference<java.util.List<CategoryResponse>>() {}, "getCategoriesByUserId");
    }

    public Mono<CategoryResponse> updateCategory(CategoryUpdateRequest request) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", request.getId());
        Map<String, Object> input = new HashMap<>();
        input.put("userId", request.getUserId());
        input.put("name", request.getName());
        input.put("type", request.getType());
        input.put("icon", request.getIcon());
        input.put("color", request.getColor());
        variables.put("input", input);
        return customAPIHandler.GraphQlApiCall(GraphqlQuries.Update_Category, variables, new TypeReference<CategoryResponse>() {}, "updateCategory");
    }

    public Mono<Boolean> deleteCategory(String categoryId, String userId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", categoryId);
        variables.put("userId", userId);
        return customAPIHandler.GraphQlApiCall(GraphqlQuries.Delete_Category, variables, new TypeReference<Boolean>() {}, "deleteCategory");
    }

}
