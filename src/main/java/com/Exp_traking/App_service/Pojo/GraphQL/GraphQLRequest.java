package com.Exp_traking.App_service.Pojo.GraphQL;

import lombok.Data;

@Data
public class GraphQLRequest {

    private String query;
    private Object variables;

    public GraphQLRequest(String query, Object variables) {
        this.query = query;
        this.variables = variables;
    }

}
