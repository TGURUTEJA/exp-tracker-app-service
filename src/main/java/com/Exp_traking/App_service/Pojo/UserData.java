package com.Exp_traking.App_service.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private int id;
    private String userName;
    private String email;
    private String password;
    private int userId;

}
