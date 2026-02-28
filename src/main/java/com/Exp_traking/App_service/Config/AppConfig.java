package com.Exp_traking.App_service.Config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration(proxyBeanMethods = false)
public class AppConfig {

  @Bean
  public WebClient webClient(WebClient.Builder builder) {
    return builder
        .filter(logRequest())
        .filter(logResponse())
        .build();
  }

  private static ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(request -> {
      System.out.println(">>> " + request.method() + " " + request.url());
      System.out.println(">>> Headers: " + request.headers());
      // Body logging for WebClient requests is non-trivial; prefer logging at call sites if needed.
      return Mono.just(request);
    });
  }

  private static ExchangeFilterFunction logResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(response -> {
      System.out.println("<<< Status: " + response.statusCode());
      System.out.println("<<< Headers: " + response.headers().asHttpHeaders());
      return logBody(response);
    });
  }

  private static Mono<org.springframework.web.reactive.function.client.ClientResponse> logBody(
      org.springframework.web.reactive.function.client.ClientResponse response) {
    // Buffer and log small response bodies safely
    return response.bodyToMono(byte[].class)
        .defaultIfEmpty(new byte[0])
        .flatMap(bytes -> {
          if (bytes.length > 0) {
            String body = new String(bytes, StandardCharsets.UTF_8);
            System.out.println("<<< Body: " + body);
          } else {
            System.out.println("<<< Body: <empty>");
          }
          // Recreate response with buffered body so downstream can read it
          return Mono.just(response.mutate()
              .body(Flux.just(org.springframework.core.io.buffer.DefaultDataBufferFactory.sharedInstance.wrap(bytes)))
              .build());
        });
  }
}
