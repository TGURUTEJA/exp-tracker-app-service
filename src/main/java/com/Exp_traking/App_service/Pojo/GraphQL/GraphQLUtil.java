package com.Exp_traking.App_service.Pojo.GraphQL;

public class GraphQLUtil {
    public static <T> T extract(GraphQLResponse<T> response) {
        if (response.hasErrors()) {
            throw new RuntimeException(response.getErrors().getFirst().getMessage());
        }
        return response.getData();
    }
}
