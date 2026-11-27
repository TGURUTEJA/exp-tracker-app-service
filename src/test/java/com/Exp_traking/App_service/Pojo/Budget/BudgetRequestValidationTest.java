package com.Exp_traking.App_service.Pojo.Budget;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BudgetRequestValidationTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void createValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        factory.close();
    }

    @Test
    void acceptsAWellFormedBudget() {
        assertTrue(validator.validate(validRequest()).isEmpty());
    }

    @Test
    void rejectsNonPositiveAmounts() {
        BudgetRequest request = validRequest();
        request.setAmount(BigDecimal.ZERO);

        assertFalse(validator.validate(request).isEmpty());
    }

    @Test
    void rejectsOutOfRangePeriodValues() {
        BudgetRequest request = validRequest();
        request.setMonth(13);

        assertFalse(validator.validate(request).isEmpty());

        request.setMonth(11);
        request.setYear(1800);

        assertFalse(validator.validate(request).isEmpty());
    }

    @Test
    void rejectsMissingCategory() {
        BudgetRequest request = validRequest();
        request.setCategoryId(null);

        assertFalse(validator.validate(request).isEmpty());
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
}
