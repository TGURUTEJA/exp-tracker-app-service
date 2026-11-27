package com.Exp_traking.App_service.Controller;

import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Pojo.Budget.BudgetDetails;
import com.Exp_traking.App_service.Pojo.Budget.BudgetRequest;
import com.Exp_traking.App_service.Services.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class BudgetControllerTest {

    private BudgetService budgetService;
    private BudgetController controller;
    private ServerWebExchange exchange;

    @BeforeEach
    void setUp() {
        budgetService = mock(BudgetService.class);
        controller = new BudgetController(budgetService);
        exchange = mock(ServerWebExchange.class);
    }

    @Test
    void listUsesJwtUserIdWhenNoPeriodIsRequested() {
        when(exchange.getAttribute("ID")).thenReturn("verified-user");
        when(budgetService.getBudgets("verified-user")).thenReturn(Mono.just(List.of()));

        StepVerifier.create(controller.getBudgets(exchange, null, null))
                .expectNext(List.of())
                .verifyComplete();

        verify(budgetService).getBudgets("verified-user");
    }

    @Test
    void listDelegatesRequestedPeriodToTheDateRangeQuery() {
        when(exchange.getAttribute("ID")).thenReturn("verified-user");
        when(budgetService.getBudgets("verified-user", 2026, 11)).thenReturn(Mono.just(List.of()));

        StepVerifier.create(controller.getBudgets(exchange, 2026, 11))
                .expectNext(List.of())
                .verifyComplete();

        verify(budgetService).getBudgets("verified-user", 2026, 11);
    }

    @Test
    void listRejectsAPartialPeriod() {
        when(exchange.getAttribute("ID")).thenReturn("verified-user");

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.getBudgets(exchange, 2026, null)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verifyNoInteractions(budgetService);
    }

    @Test
    void listRejectsRequestsWithoutVerifiedUserId() {
        assertThrows(ResponseStatusException.class, () -> controller.getBudgets(exchange, null, null));

        verifyNoInteractions(budgetService);
    }

    @Test
    void createUsesJwtUserIdRatherThanAcceptingOneFromRequest() {
        when(exchange.getAttribute("ID")).thenReturn("verified-user");
        BudgetRequest request = validRequest();
        ApiResponse<BudgetDetails> response = new ApiResponse<>();
        response.setError(false);
        when(budgetService.createBudget("verified-user", request)).thenReturn(Mono.just(response));

        StepVerifier.create(controller.createBudget(exchange, request))
                .expectNext(response)
                .verifyComplete();

        verify(budgetService).createBudget("verified-user", request);
    }

    @Test
    void updateScopesTheRequestToPathIdAndJwtUserId() {
        when(exchange.getAttribute("ID")).thenReturn("verified-user");
        BudgetRequest request = validRequest();
        ApiResponse<BudgetDetails> response = new ApiResponse<>();
        response.setError(false);
        when(budgetService.updateBudget("12", "verified-user", request)).thenReturn(Mono.just(response));

        StepVerifier.create(controller.updateBudget(exchange, "12", request))
                .expectNext(response)
                .verifyComplete();

        verify(budgetService).updateBudget("12", "verified-user", request);
    }

    @Test
    void deleteScopesTheRequestToJwtUserId() {
        when(exchange.getAttribute("ID")).thenReturn("verified-user");
        ApiResponse<Boolean> response = new ApiResponse<>();
        response.setError(false);
        response.setData(true);
        when(budgetService.deleteBudget("12", "verified-user")).thenReturn(Mono.just(response));

        StepVerifier.create(controller.deleteBudget(exchange, "12"))
                .expectNext(response)
                .verifyComplete();

        verify(budgetService).deleteBudget("12", "verified-user");
    }

    @Test
    void writesRejectRequestsWithoutVerifiedUserId() {
        BudgetRequest request = validRequest();

        assertThrows(ResponseStatusException.class, () -> controller.createBudget(exchange, request));
        assertThrows(ResponseStatusException.class, () -> controller.updateBudget(exchange, "12", request));
        assertThrows(ResponseStatusException.class, () -> controller.deleteBudget(exchange, "12"));

        verifyNoInteractions(budgetService);
    }

    private BudgetRequest validRequest() {
        BudgetRequest request = new BudgetRequest();
        request.setCategoryId(4L);
        request.setYear(2026);
        request.setMonth(11);
        request.setAmount(new BigDecimal("1500.00"));
        request.setIsActive(true);
        request.setRecurrence("monthly");
        return request;
    }

    private org.springframework.http.HttpStatus HttpStatusBad_REQUEST() {
        return org.springframework.http.HttpStatus.BAD_REQUEST;
    }
}
