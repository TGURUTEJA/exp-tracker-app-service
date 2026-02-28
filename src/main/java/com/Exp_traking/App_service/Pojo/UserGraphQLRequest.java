package com.Exp_traking.App_service.Pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserGraphQLRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String dob;
}
