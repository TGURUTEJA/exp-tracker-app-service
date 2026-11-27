package com.Exp_traking.App_service.Services.Categoty;

import com.Exp_traking.App_service.ApiHelper.CategoryAPIHelper;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.Category.CategoryCreateRequest;
import com.Exp_traking.App_service.Pojo.Category.CategoryCreateResponse;
import com.Exp_traking.App_service.Pojo.Category.CategoryUpdateRequest;
import com.Exp_traking.App_service.Pojo.Category.CategoryDeleteRequest;
import com.Exp_traking.App_service.Pojo.Category.CategoryDeleteResponse;
import com.Exp_traking.App_service.Pojo.GraphQL.GraphQLException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.Exp_traking.App_service.Pojo.Category.CategoryResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategotyService {

    private final CategoryAPIHelper categoryAPIHelper;

    public Mono<ApiResponse<CategoryCreateResponse>> CreateCategoryService(CategoryCreateRequest request) {
        ApiResponse<CategoryCreateResponse> response = new ApiResponse<>();
        return categoryAPIHelper.cresteCategoty(request)
                .map(categoryCreateResponse -> {
                    response.setData(categoryCreateResponse);
                    response.setError(false);
                    response.setMessage("Category created successfully");
                    return response;
                })
                .onErrorResume(error -> {
                    response.setError(true);
                    if (error instanceof GraphQLException ex) {
                        response.setMessage(
                                "Failed to create category [" + ex.getCode() + "]: " + ex.getMessage()
                        );
                    } else {
                        response.setMessage(
                                "Failed to create category: " + error.getMessage()
                        );
                    }
                    return Mono.just(response);
                });
    }

    public Mono<ApiResponse<List<CategoryResponse>>> GetCategoriesService(String userId) {
        ApiResponse<List<CategoryResponse>> response = new ApiResponse<>();
        return categoryAPIHelper.getCategoriesByUserId(userId)
                .map(categoryList -> {
                    response.setData(categoryList);
                    response.setError(false);
                    response.setMessage("Categories fetched successfully");
                    return response;
                })
                .onErrorResume(error -> {
                    response.setError(true);
                    if (error instanceof GraphQLException ex) {
                        response.setMessage("Failed to get categories [" + ex.getCode() + "]: " + ex.getMessage());
                    } else {
                        response.setMessage("Failed to get categories: " + error.getMessage());
                    }
                    return Mono.just(response);
                });
    }

    public Mono<ApiResponse<CategoryResponse>> UpdateCategoryService(CategoryUpdateRequest request) {
        ApiResponse<CategoryResponse> response = new ApiResponse<>();
        return categoryAPIHelper.updateCategory(request)
                .map(categoryResponse -> {
                    response.setData(categoryResponse);
                    response.setError(false);
                    response.setMessage("Category updated successfully");
                    return response;
                })
                .onErrorResume(error -> {
                    response.setError(true);
                    if (error instanceof GraphQLException ex) {
                        response.setMessage("Failed to update category [" + ex.getCode() + "]: " + ex.getMessage());
                    } else {
                        response.setMessage("Failed to update category: " + error.getMessage());
                    }
                    return Mono.just(response);
                });
    }

    public Mono<ApiResponse<CategoryDeleteResponse>> DeleteCategoryService(
            String categoryId,
            String userId
    ) {
        ApiResponse<CategoryDeleteResponse> response = new ApiResponse<>();
        return categoryAPIHelper.deleteCategory(categoryId, userId)
                .map(isDeleted -> {
                    CategoryDeleteResponse deleteResponse = new CategoryDeleteResponse();
                    deleteResponse.setSuccess(isDeleted);
                    response.setData(deleteResponse);
                    response.setError(!isDeleted);
                    response.setMessage(isDeleted ? "Category deleted successfully" : "Failed to delete category");
                    return response;
                })
                .onErrorResume(error -> {
                    response.setError(true);
                    if (error instanceof GraphQLException ex) {
                        response.setMessage("Failed to delete category [" + ex.getCode() + "]: " + ex.getMessage());
                    } else {
                        response.setMessage("Failed to delete category: " + error.getMessage());
                    }
                    return Mono.just(response);
                });
    }
}
