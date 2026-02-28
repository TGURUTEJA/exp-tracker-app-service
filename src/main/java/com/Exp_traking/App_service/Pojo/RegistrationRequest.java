package com.Exp_traking.App_service.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String DOB;
}
