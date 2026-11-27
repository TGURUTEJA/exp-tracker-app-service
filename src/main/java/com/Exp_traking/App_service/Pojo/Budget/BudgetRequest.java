package com.Exp_traking.App_service.Pojo.Budget;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Create and update payload for a budget. Budgets are scoped to one category and
 * one year/month period; both create and update send the full payload.
 */
@Data
@NoArgsConstructor
public class BudgetRequest {
    @NotNull
    @Positive
    private Long categoryId;

    @NotNull
    @Min(1970)
    @Max(2100)
    private Integer year;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer month;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal amount;

    private Boolean isActive;

    @Size(max = 20)
    private String recurrence;
}
