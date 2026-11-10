package com.Exp_traking.App_service.Pojo.GraphQL;

import lombok.Getter;

import java.util.List;

@Getter
public class GraphQLError {
    private String message;
    private List<Location> locations;
    private List<String> path;
    private GraphQLException extensions;
}