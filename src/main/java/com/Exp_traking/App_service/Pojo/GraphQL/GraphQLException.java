package com.Exp_traking.App_service.Pojo.GraphQL;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GraphQLException extends RuntimeException  {
    private String code;
    private String classification;

    public GraphQLException(String code, String message) {
        this.code = code;
        this.classification = message;
    }
}