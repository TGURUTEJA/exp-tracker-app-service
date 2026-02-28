package com.Exp_traking.App_service.Filter;

import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.Exp_traking.App_service.Pojo.UserRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthWebFilter implements WebFilter {

    private final RSAPublicKey publicKey;

    // configure whitelist here
    private static final List<String> WHITELIST = Arrays.asList(
            "/api/status",
            "/api/register",
            "/auth/login",
            "/auth/refresh",
            "/static/",    // any static content
            "/favicon.ico"
    );

    public JwtAuthWebFilter() {
        this.publicKey = RequestFilter.loadPublicKeyFromPem("public.pem"); // keep on classpath
    }

    private boolean isWhitelisted(String path) {
        return WHITELIST.stream().anyMatch(path::startsWith);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("JwtAuthWebFilter: Filtering request " + exchange.getRequest().getURI());
        String path = exchange.getRequest().getPath().pathWithinApplication().value();

        if (isWhitelisted(path)) {
            return chain.filter(exchange);
        }

        // Prefer cookie, then Authorization header
        var cookie = exchange.getRequest().getCookies().getFirst("access_token");
        String token = null;
        if (cookie != null && !cookie.getValue().isBlank()) {
            token = cookie.getValue();
        } else {
            // Authorization: Bearer ...
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
        }

        if (token == null || token.isBlank()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build();
            Jws<Claims> claims = parser.parseClaimsJws(token);

            // Reject password_reset_token tokens or revoked tokens as needed
            boolean isPasswordResetToken = Boolean.TRUE.equals(claims.getBody().get("password_reset_token", Boolean.class));
            if (isPasswordResetToken) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String ID = String.valueOf(claims.getBody().get("ID", Integer.class));
            exchange.getAttributes().put("ID", ID);
            System.out.println("JwtAuthWebFilter: Authenticated user " + ID);
            return chain.filter(exchange);
        } catch (io.jsonwebtoken.JwtException e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        }
    }
}
