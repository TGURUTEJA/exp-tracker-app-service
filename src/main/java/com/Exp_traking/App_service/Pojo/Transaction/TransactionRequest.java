package com.Exp_traking.App_service.Pojo.Transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransactionRequest {
    @NotNull
    @Positive
    private Double accountId;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal amount;

    @NotNull
    @Pattern(regexp = "(?i)debit|credit")
    private String debitCredit;

    @Positive
    private Integer categoryId;

    private String description;
    private String created;
}
