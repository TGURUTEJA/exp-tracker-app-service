package com.Exp_traking.App_service.Pojo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterUserDBResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String dob;
}
