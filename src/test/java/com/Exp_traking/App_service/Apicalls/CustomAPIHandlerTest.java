package com.Exp_traking.App_service.Apicalls;

import com.Exp_traking.App_service.Pojo.Account.AccountDetails;
import com.Exp_traking.App_service.Pojo.Account.AccountExtraData;
import com.Exp_traking.App_service.Pojo.Account.CreditCardPojo;
import com.Exp_traking.App_service.Pojo.Account.EMIAccountPojo;
import com.Exp_traking.App_service.Pojo.Account.FriendAccountPojo;
import com.Exp_traking.App_service.Pojo.Account.InvestmentAccountPojo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CustomAPIHandlerTest {

    @Test
    void serializesSubtypeLocalDatesInGraphQlVariables() {
        CustomAPIHandler handler = new CustomAPIHandler(
                WebClient.builder(),
                new Util(),
                new ObjectMapper(),
                "http://localhost:8081/graphql",
                "http://localhost:8082"
        );
        AccountDetails account = new AccountDetails();
        account.setType("CREDIT_CARD");
        account.setDisplayName("AXIS");
        account.setCurrency("INR");
        account.setBalance(BigDecimal.ZERO);

        CreditCardPojo creditCard = new CreditCardPojo();
        creditCard.setOutstanding(BigDecimal.ZERO);
        creditCard.setCreditLimit(new BigDecimal("100000"));
        creditCard.setDueDate(LocalDate.of(2026, 11, 30));
        creditCard.setBillDate(LocalDate.of(2026, 11, 12));
        AccountExtraData additionalData = new AccountExtraData();
        additionalData.setCreditCard(creditCard);
        account.setAdditionalData(additionalData);

        JsonNode input = handler
                .buildGraphQlRequestBody("mutation CreateAccount", account)
                .path("variables")
                .path("input")
                .path("additionalData")
                .path("creditCard");

        assertEquals("2026-11-30", input.path("dueDate").asText());
        assertEquals("2026-11-12", input.path("billDate").asText());
        assertEquals(100000, input.path("creditLimit").asInt());
    }

    @Test
    void serializesOnlyGraphQlInputFieldsForSubtypeAccounts() {
        CustomAPIHandler handler = new CustomAPIHandler(
                WebClient.builder(),
                new Util(),
                new ObjectMapper(),
                "http://localhost:8081/graphql",
                "http://localhost:8082"
        );
        AccountDetails account = new AccountDetails();
        AccountExtraData additionalData = new AccountExtraData();

        EMIAccountPojo emi = new EMIAccountPojo();
        emi.setPrincipalAmount(new BigDecimal("50000"));
        emi.setStartDate(LocalDate.of(2026, 11, 25));
        additionalData.setEmi(emi);

        InvestmentAccountPojo investment = new InvestmentAccountPojo();
        investment.setInvestmentType("FD");
        additionalData.setInvestment(investment);

        FriendAccountPojo friend = new FriendAccountPojo();
        friend.setFriendName("Alex");
        additionalData.setFriend(friend);
        account.setAdditionalData(additionalData);

        JsonNode input = handler
                .buildGraphQlRequestBody("mutation CreateAccount", account)
                .path("variables")
                .path("input")
                .path("additionalData");

        for (String subtype : new String[] {"emi", "investment", "friend"}) {
            assertFalse(input.path(subtype).has("id"));
            assertFalse(input.path(subtype).has("accountId"));
        }
        assertEquals(50000, input.path("emi").path("principalAmount").asInt());
        assertEquals("2026-11-25", input.path("emi").path("startDate").asText());
        assertEquals("FD", input.path("investment").path("investmentType").asText());
        assertEquals("Alex", input.path("friend").path("friendName").asText());
    }
}
