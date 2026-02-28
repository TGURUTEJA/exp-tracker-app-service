package com.Exp_traking.App_service.Apicalls;


import com.Exp_traking.App_service.Pojo.ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomAPIHandler {

    private final WebClient webClient;
    private final WebClient webClientGraphQL;
    private final Util util;

    private final ObjectMapper mapper;


    public CustomAPIHandler(WebClient.Builder builder, Util util,ObjectMapper mapper) {
        this.webClient = builder
                .baseUrl("http://localhost:8082")
                .build();
        this.webClientGraphQL = builder
                .baseUrl("http://localhost:8081/graphql")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.util = util;
        this.mapper = mapper;
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private JsonNode toJson(Object value) {
        return new ObjectMapper().valueToTree(value);
    }

    public <T,P> Mono<T> customApicall(String apiPath, Class<T> responseType, String method, P Request) {
        return switch (method.toUpperCase()) {
            case "GET" -> webClient.get()
                    .uri(apiPath)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(responseType);
            case "POST" -> {
                System.out.println("UserCredAPIHandelar POST: " + apiPath + " " + Request);
                yield webClient.post()
                        .uri(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .bodyValue(Request)
                        .exchangeToMono(clientResponse -> {
                            if (clientResponse.statusCode().isError()) {
                                return clientResponse.bodyToMono(responseType)
                                        .switchIfEmpty(Mono.error(new RuntimeException("Empty error response body")))
                                        .onErrorResume(e -> Mono.error(new RuntimeException("Error parsing response body", e)));
                            } else {
                                // Normal successful response
                                return clientResponse.bodyToMono(responseType);
                            }
                        });
            }
            case "PUT" -> webClient.put()
                    .uri(apiPath)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(Request)
                    .retrieve()
                    .bodyToMono(responseType);
            case "DELETE" ->
                // If the API expects a body with DELETE, use method(HttpMethod.DELETE) + body
                    webClient.method(HttpMethod.DELETE)
                            .uri(apiPath)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(Request))
                            .retrieve()
                            .bodyToMono(responseType);
            default -> Mono.error(new IllegalArgumentException("Unsupported method: " + method));
        };
    }



    public <V,R> Mono<ApiResponse<R>> GraphQlCustomApiCall(String query, V variables, TypeReference<R> ResultClass, String ResultPath) {
        ObjectNode requestBody = JsonNodeFactory.instance.objectNode();
        requestBody.put("query", query);
        if(variables != null){
            requestBody.set("variables", JsonNodeFactory.instance.objectNode().set("input", toJson(variables)));
        } else {
            requestBody.set("variables", JsonNodeFactory.instance.objectNode());
        }
        return webClientGraphQL.post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMap(json -> {
                    if(json.has("errors")) {
                        return util.buildApiErrorResponse("GraphQL errors: " + json.get("errors").toString(), null);
                    }
                    JsonNode node = json.path("data");
                    if(ResultPath != null && !ResultPath.isBlank()) {
                        for(String path : ResultPath.split("\\.")) {
                            node = node.path(path);
                        }
                    }
                    if(node.isMissingNode() || node.isNull()) {
                        return util.buildApiErrorResponse("Missing expected data in response", null);
                    }
                    try{
                        JsonNode Response = node.path("data");
                        boolean isError = node.path("error").asBoolean(false);
                        String message = node.path("message").asText("");
                        if(Response.isMissingNode() || Response.isNull()) {
                            if(isError) {
                                return util.buildApiErrorResponse("DB API Error: " + message, null);
                            }
                            return util.buildApiErrorResponse("Missing expected response in response", null);
                        }
                        if(isError) {
                            return util.buildApiErrorResponse("DB API Error: " + message, null);
                        }
                        R result = mapper.convertValue(Response, ResultClass);
                        return util.buildApiSuccessResponse(message, result);
                    } catch (Exception e){
                        return  util.buildApiErrorResponse("Error parsing response data: " + e.getMessage(), null);
                    }
                });
    }

}
