package com.Exp_traking.App_service.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    boolean isError;
    List<CheckMessage> errorData;
    String message;
    String ID;
}
