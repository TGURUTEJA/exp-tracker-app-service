package com.Exp_traking.App_service.Controller;

import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.RegisterUserDBResponse;
import com.Exp_traking.App_service.Pojo.UserProfileUpdateRequest;
import com.Exp_traking.App_service.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserService userService;
    private UserController controller;
    private ServerWebExchange exchange;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        controller = new UserController(userService, null);
        exchange = mock(ServerWebExchange.class);
    }

    @Test
    void profileUpdateUsesAuthenticatedUserId() {
        when(exchange.getAttribute("ID")).thenReturn("authenticated-user");
        UserProfileUpdateRequest request = new UserProfileUpdateRequest();
        request.setFirstName("Ada");
        request.setLastName("Lovelace");
        request.setDob(LocalDate.parse("1815-12-10"));
        ApiResponse<RegisterUserDBResponse> response = new ApiResponse<>();
        response.setError(false);
        when(userService.updateUserDetails("authenticated-user", request)).thenReturn(Mono.just(response));

        StepVerifier.create(controller.updateUserDetails(exchange, request))
                .expectNext(response)
                .verifyComplete();

        verify(userService).updateUserDetails("authenticated-user", request);
    }

    @Test
    void profileUpdateRejectsMissingAuthenticatedUserId() {
        assertThrows(
                ResponseStatusException.class,
                () -> controller.updateUserDetails(exchange, new UserProfileUpdateRequest())
        );

        verifyNoInteractions(userService);
    }
}
