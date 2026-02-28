package com.Exp_traking.App_service.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegisterRequest {
    private String userName;
    private String password;
    private String email;
}
