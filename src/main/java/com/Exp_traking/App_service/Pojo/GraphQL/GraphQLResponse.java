package com.Exp_traking.App_service.Pojo.GraphQL;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GraphQLResponse<T> {
    private T data;
    private List<GraphQLError> errors;

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

}
