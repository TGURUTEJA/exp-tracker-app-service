package com.Exp_traking.App_service.Controller;

import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.Category.CategoryCreateRequest;
import com.Exp_traking.App_service.Pojo.Category.CategoryCreateResponse;
import com.Exp_traking.App_service.Pojo.Category.CategoryResponse;
import com.Exp_traking.App_service.Pojo.Category.CategoryUpdateRequest;
import com.Exp_traking.App_service.Pojo.Category.CategoryDeleteResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import com.Exp_traking.App_service.Services.Categoty.CategotyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/App/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategotyService categotyService;

    @PostMapping("/create_category")
    public Mono<ApiResponse<CategoryCreateResponse>> CreateCategory(ServerWebExchange exchange, @RequestBody CategoryCreateRequest request) {
        String userId = requireUserId(exchange);
        request.setUserId(userId);
        return categotyService.CreateCategoryService(request);
    }

    @GetMapping("/get_category")
    public Mono<ApiResponse<List<CategoryResponse>>> GetCategory(ServerWebExchange exchange) {
        String userId = requireUserId(exchange);
        return categotyService.GetCategoriesService(userId);
    }

    @PutMapping("/update_category")
    public Mono<ApiResponse<CategoryResponse>> UpdateCategory(ServerWebExchange exchange, @RequestBody CategoryUpdateRequest request) {
        String userId = requireUserId(exchange);
        request.setUserId(userId);
        return categotyService.UpdateCategoryService(request);
    }

    @DeleteMapping("/delete_category/{categoryId}")
    public Mono<ApiResponse<CategoryDeleteResponse>> DeleteCategory(ServerWebExchange exchange, @PathVariable String categoryId) {
        String userId = requireUserId(exchange);
        return categotyService.DeleteCategoryService(categoryId, userId);
    }

    private String requireUserId(ServerWebExchange exchange) {
        String userId = exchange.getAttribute("ID");
        if (userId == null || userId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }
        return userId;
    }

}
