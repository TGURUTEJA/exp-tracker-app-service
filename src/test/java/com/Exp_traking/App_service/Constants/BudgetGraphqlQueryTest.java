package com.Exp_traking.App_service.Constants;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BudgetGraphqlQueryTest {

    private static final List<String> QUERIES = List.of(
            GraphqlQuries.Get_Budgets_By_UserID,
            GraphqlQuries.Get_Budgets_By_UserID_And_Period,
            GraphqlQuries.Create_Budget,
            GraphqlQuries.Update_Budget,
            GraphqlQuries.Delete_Budget
    );

    @Test
    void budgetQueriesDoNotRequestDateTimeFieldsUnsupportedByTheDbScalar() {
        for (String query : QUERIES) {
            assertFalse(query.contains("createdAt"), "createdAt is not serializable by the DB DateTime scalar");
            assertFalse(query.contains("updatedAt"), "updatedAt is not serializable by the DB DateTime scalar");
        }
    }

    @Test
    void budgetQueriesMatchTheDbRootFields() {
        assertTrue(GraphqlQuries.Get_Budgets_By_UserID.contains("getBudgetsByUserId"));
        assertTrue(GraphqlQuries.Get_Budgets_By_UserID_And_Period.contains("getBudgetsByUserIdAndDateRange"));
        assertTrue(GraphqlQuries.Create_Budget.contains("createBudget"));
        assertTrue(GraphqlQuries.Update_Budget.contains("updateBudget"));
        assertTrue(GraphqlQuries.Delete_Budget.contains("deleteBudget"));
    }
}
