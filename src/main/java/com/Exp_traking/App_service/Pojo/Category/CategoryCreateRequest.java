package com.Exp_traking.App_service.Pojo.Category;

import lombok.Data;

@Data
public class CategoryCreateRequest {
    String userId;
    String name;
    String type;
    String icon;
    String color;
}
