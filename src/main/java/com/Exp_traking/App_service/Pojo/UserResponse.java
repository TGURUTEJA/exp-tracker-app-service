package com.Exp_traking.App_service.Pojo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.Exp_traking.App_service.Pojo.UserData;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String message;
    private java.util.List<UserData> userData;
    private boolean error;
    private String status;
}
