package com.Exp_traking.App_service.Constants;

public class GraphqlQuries {

    public static final String Create_User =  """
            mutation CreateUser($input: UserInput!) {
              createUser(input: $input) {
                message
                error
                data {
                  id
                  firstName
                  lastName
                  dob
                }
              }
            }
        """;

    public static final String Get_User_By_ID = """
            query getUserById($input: IDInput!) {
                getUserById(input: $input) {
                    message
                    error
                    data {
                        id
                        firstName
                        lastName
                        dob
                     }
                }
            }
        """;

    public static final String Get_Accouts_By_UserID = """
            query GetAccountsByUserId($input: IDInput!) {
                  getAccountsByUserId(input: $input) {
                    error
                    message
                    data {
                      id
                      userId
                      type
                      displayName
                      balance
                      currency
                      status
                      createdAt
                      additionalData {
                        creditCard {
                          outstanding
                          creditLimit
                          dueDate
                          billDate
                        }
                        emi {
                          principalAmount
                          interestRate
                          emiAmount
                          totalInstallments
                          paidInstallments
                          startDate
                          endDate
                          nextDueDate
                        }
                        investment {
                          investmentType
                          purchaseDate
                          maturityDate
                          amountInvested
                          currentValue
                          expectedReturn
                        }
                        friend {
                          friendName
                          contactInfo
                          outstandingAmount
                          loanStartDate
                          loanDueDate
                        }
                      }
                    }
                  }
                }
        """;

    public static final String Get_Transactions_By_UserID = """
            query GetTransactionsByUserId($userId: String!) {
              getTransactionsByUserId(userId: $userId) {
                id
                userId
                accountId
                amount
                debitCredit
                categoryId
                description
                created
                accountBalance
              }
            }
        """;

    public static final String Get_Filtered_Transactions_By_UserID = """
            query GetTransactionsByUserId(
              $userId: String!,
              $startDate: String,
              $endDate: String,
              $accountId: Float,
              $categoryId: Int,
              $newestFirst: Boolean
            ) {
              getTransactionsByUserId(
                userId: $userId,
                startDate: $startDate,
                endDate: $endDate,
                accountId: $accountId,
                categoryId: $categoryId,
                newestFirst: $newestFirst
              ) {
                id
                userId
                accountId
                amount
                debitCredit
                categoryId
                description
                created
                accountBalance
              }
            }
        """;

    public static final String Create_Transaction = """
            mutation CreateTransaction($userId: String!, $input: TransactionPojo!) {
              createTransaction(userId: $userId, input: $input) {
                message
                error
                data {
                  id
                  userId
                  accountId
                  amount
                  debitCredit
                  categoryId
                  description
                  created
                  accountBalance
                }
              }
            }
        """;

    public static final String Update_Transaction = """
            mutation UpdateTransaction($id: Int!, $userId: String!, $input: TransactionPojo!) {
              updateTransaction(id: $id, userId: $userId, input: $input) {
                message
                error
                data {
                  id
                  userId
                  accountId
                  amount
                  debitCredit
                  categoryId
                  description
                  created
                  accountBalance
                }
              }
            }
        """;

    public static final String Delete_Transaction = """
            mutation DeleteTransaction($id: Int!, $userId: String!) {
              deleteTransaction(id: $id, userId: $userId) {
                message
                error
                data
              }
            }
        """;

    public static final String Create_Account = """
            mutation CreateAccount($input: AccountInput!) {
              createAccount(body: $input) {
                error
                message
                data {
                  id
                  userId
                  type
                  displayName
                  balance
                  currency
                  status
                  createdAt
                }
              }
            }
        """;

    public static final String Create_Category =  """
                mutation($input: CategoryInput!) {
                    createCategory(input: $input) {
                        id
                        name
                        type
                        icon
                        color
                    }
                }
        """;

     public static final String Create_Multiple_Categories = """
            mutation CreateMultipleCategories($input: [CategoryInput!]!) {
              createMultipleCategories(input: $input) {
                message
                error
                data {
                  id
                  userId
                  name
                  type
                  icon
                  color
                }
              }
            }
        """;

    public static final String Get_Categories_By_UserID = """
            query GetCategoriesByUserId($userId: String!) {
              getCategoriesByUserId(userId: $userId) {
                id
                name
                type
                icon
                color
              }
            }
        """;

    public static final String Update_Category = """
            mutation UpdateCategory($id: ID!, $input: CategoryInput!) {
              updateCategory(id: $id, input: $input) {
                id
                name
                type
                icon
                color
              }
            }
        """;

    public static final String Delete_Category = """
            mutation DeleteCategory($id: ID!, $userId: String!) {
              deleteCategory(id: $id, userId: $userId)
            }
        """;

    public static final String Get_Budgets_By_UserID = """
            query GetBudgetsByUserId($userId: String!) {
              getBudgetsByUserId(userId: $userId) {
                id
                userId
                categoryId
                year
                month
                amount
                isActive
                recurrence
              }
            }
        """;

    public static final String Get_Budgets_By_UserID_And_Period = """
            query GetBudgetsByUserIdAndDateRange(
              $userId: String!,
              $startYear: Int!,
              $startMonth: Int!,
              $endYear: Int!,
              $endMonth: Int!
            ) {
              getBudgetsByUserIdAndDateRange(
                userId: $userId,
                startYear: $startYear,
                startMonth: $startMonth,
                endYear: $endYear,
                endMonth: $endMonth
              ) {
                id
                userId
                categoryId
                year
                month
                amount
                isActive
                recurrence
              }
            }
        """;

    public static final String Create_Budget = """
            mutation CreateBudget(
              $userId: String!,
              $categoryId: Int!,
              $year: Int!,
              $month: Int!,
              $amount: BigDecimal!,
              $isActive: Boolean,
              $recurrence: String
            ) {
              createBudget(
                userId: $userId,
                categoryId: $categoryId,
                year: $year,
                month: $month,
                amount: $amount,
                isActive: $isActive,
                recurrence: $recurrence
              ) {
                id
                userId
                categoryId
                year
                month
                amount
                isActive
                recurrence
              }
            }
        """;

    public static final String Update_Budget = """
            mutation UpdateBudget(
              $id: ID!,
              $userId: String!,
              $categoryId: Int,
              $year: Int,
              $month: Int,
              $amount: BigDecimal,
              $isActive: Boolean,
              $recurrence: String
            ) {
              updateBudget(
                id: $id,
                userId: $userId,
                categoryId: $categoryId,
                year: $year,
                month: $month,
                amount: $amount,
                isActive: $isActive,
                recurrence: $recurrence
              ) {
                id
                userId
                categoryId
                year
                month
                amount
                isActive
                recurrence
              }
            }
        """;

    public static final String Delete_Budget = """
            mutation DeleteBudget($id: ID!, $userId: String!) {
              deleteBudget(id: $id, userId: $userId)
            }
        """;
}
