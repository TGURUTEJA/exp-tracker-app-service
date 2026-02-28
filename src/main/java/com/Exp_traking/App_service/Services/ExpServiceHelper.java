package com.Exp_traking.App_service.Services;

import com.Exp_traking.App_service.ApiHelper.AuthAPIHelper;
import com.Exp_traking.App_service.Apicalls.CustomAPIHandler;
import com.Exp_traking.App_service.Pojo.CheckMessage;
import com.Exp_traking.App_service.Pojo.RegistrationRequest;
import com.Exp_traking.App_service.Pojo.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ExpServiceHelper {
    final private AuthAPIHelper authAPIHelper;
    public RegistrationResponse registrationRequestCheck(RegistrationRequest request){
        boolean isError = false;
        RegistrationResponse response = new RegistrationResponse();
        if(request == null){
            response.setError(true);
            response.setMessage("Request body is missing");
            return response;
        }
        if(isBlank(request.getUserName())){
            isError = true;
            response.getErrorData().add(new CheckMessage(true, "UserName", "UserName is missing"));
        }
        if(isBlank(request.getEmail())){
            isError = true;
            response.getErrorData().add(new CheckMessage(true, "email", "Email is missing"));
        }
        if(isBlank(request.getPassword())){
            isError = true;
            response.getErrorData().add(new CheckMessage(true, "Password", "Password is missing"));
        }
        response.setError(isError);
        response.setMessage(isError ? "Invalid Request":"Request is valid");
        return response;
    }
    
    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

}
