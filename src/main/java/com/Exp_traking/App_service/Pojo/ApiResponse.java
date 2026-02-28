package com.Exp_traking.App_service.Pojo;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String message;
    private boolean error;
    private T data;

}
