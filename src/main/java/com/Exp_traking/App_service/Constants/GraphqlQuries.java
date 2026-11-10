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
                          nextDueDate
                        }
                        investment {
                          investmentType
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
              }
            }
        """;

    public static final String Update_Category = """
            mutation UpdateCategory($id: ID!, $input: CategoryInput!) {
              updateCategory(id: $id, input: $input) {
                id
                name
                type
              }
            }
        """;

    public static final String Delete_Category = """
            mutation DeleteCategory($id: ID!) {
              deleteCategory(id: $id)
            }
        """;
}
