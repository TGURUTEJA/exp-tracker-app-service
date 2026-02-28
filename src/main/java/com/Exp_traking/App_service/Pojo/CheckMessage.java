package com.Exp_traking.App_service.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckMessage {
    boolean isError;
    String field;
    String message;
}
