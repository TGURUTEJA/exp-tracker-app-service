package com.Exp_traking.App_service.Pojo.Budget;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class BudgetDetails {
    private String id;
    private String userId;
    private Integer categoryId;
    private Integer year;
    private Integer month;
    private BigDecimal amount;
    private Boolean isActive;
    private String recurrence;
    private String createdAt;
    private String updatedAt;
}
