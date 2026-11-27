package com.Exp_traking.App_service.Controller;

import com.Exp_traking.App_service.Pojo.Transaction.TransactionDetails;
import com.Exp_traking.App_service.Pojo.Transaction.TransactionRequest;
import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.Exp_traking.App_service.Services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class TransactionControllerTest {

    private TransactionService transactionService;
    private TransactionController controller;
    private ServerWebExchange exchange;

    @BeforeEach
    void setUp() {
        transactionService = mock(TransactionService.class);
        controller = new TransactionController(transactionService);
        exchange = mock(ServerWebExchange.class);
    }

    @Test
    void filteredListUsesJwtUserIdAndProvidedFilters() {
        when(exchange.getAttribute("ID")).thenReturn("verified-user");
        when(transactionService.getTransactionsByUserId(
                "verified-user", "2026-01-01T00:00:00", null, 17.0, 3
        )).thenReturn(Mono.just(List.of()));

        StepVerifier.create(controller.getTransactions(
                        exchange, "2026-01-01T00:00:00", null, 17.0, 3
                ))
                .expectNext(List.of())
                .verifyComplete();

        verify(transactionService).getTransactionsByUserId(
                "verified-user", "2026-01-01T00:00:00", null, 17.0, 3
        );
    }

    @Test
    void historyRejectsRequestsWithoutVerifiedUserId() {
        assertThrows(ResponseStatusException.class, () -> controller.getUserTransactionHistory(exchange));

        verifyNoInteractions(transactionService);
    }

    @Test
    void legacyHistoryKeepsItsUnfilteredServiceContract() {
        when(exchange.getAttribute("ID")).thenReturn("verified-user");
        when(transactionService.getTransactionsByUserId("verified-user"))
                .thenReturn(Mono.just(List.of()));

        StepVerifier.create(controller.getUserTransactionHistory(exchange))
                .expectNext(List.of())
                .verifyComplete();

        verify(transactionService).getTransactionsByUserId("verified-user");
    }

    @Test
    void createUsesJwtUserIdRatherThanAcceptingOneFromRequest() {
        when(exchange.getAttribute("ID")).thenReturn("verified-user");
        TransactionRequest request = new TransactionRequest();
        ApiResponse<TransactionDetails> response = new ApiResponse<>();
        response.setError(false);
        when(transactionService.createTransaction("verified-user", request))
                .thenReturn(Mono.just(response));

        StepVerifier.create(controller.createTransaction(exchange, request))
                .expectNext(response)
                .verifyComplete();

        verify(transactionService).createTransaction("verified-user", request);
    }
}
