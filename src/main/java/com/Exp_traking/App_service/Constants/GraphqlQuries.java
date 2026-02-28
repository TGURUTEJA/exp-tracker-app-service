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
}
