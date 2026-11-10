package com.Exp_traking.App_service.Pojo.Category;

import lombok.Data;

@Data
public class CategoryUpdateRequest {
    private String id;
    private String userId;
    private String name;
    private String type;
}

