package com.Exp_traking.App_service.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
  private final List<String> allowedOrigins;

  public SecurityConfig(
      @Value ("${app.cors.allowed-origins}") String allowedOriginsCsv) {
    this.allowedOrigins = Arrays.stream(allowedOriginsCsv.split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .toList();
  }

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeExchange(ex -> ex
                    .anyExchange().permitAll()    // <-- VERY IMPORTANT
            )
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .logout(ServerHttpSecurity.LogoutSpec::disable);

    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    System.out.println("Allowed Origins: " + allowedOrigins);
    CorsConfiguration cfg = new CorsConfiguration();
    cfg.setAllowedOrigins(allowedOrigins); // your UI
    cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    cfg.setAllowedHeaders(List.of("*"));
    cfg.setAllowCredentials(true);
    cfg.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);
    return source;
  }
  // @Bean
  //   public WebSessionManager webSessionManager() {
  //       DefaultWebSessionManager manager = new DefaultWebSessionManager();
  //       manager.setSessionStore(new InMemoryWebSessionStore()); // or RedisWebSessionStore
  //       HeaderWebSessionIdResolver resolver = new HeaderWebSessionIdResolver();
  //       resolver.setHeaderName("X-Auth-Token");
  //       manager.setSessionIdResolver(resolver);
  //       return manager;
  //   }
}
