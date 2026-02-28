package com.Exp_traking.App_service.Filter;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.function.BiFunction;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.Exp_traking.App_service.Pojo.UserRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class RequestFilter {

    public <T> Mono<ResponseEntity<T>> biFilterRequest(
            ServerWebExchange exchange,
            BiFunction<ServerWebExchange, UserRequest, Mono<T>> serviceMethod) {
        System.out.println("Inside biFilterRequest");
       // String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        String cookieToken = null;

        // Prefer cookie
        org.springframework.http.HttpCookie cookie = exchange.getRequest().getCookies().getFirst("access_token");
        if (cookie == null || cookie.getValue() == null || cookie.getValue().isBlank()) {
            System.out.println("Missing or invalid access token");
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        cookieToken = cookie.getValue();
        try {
            RSAPublicKey publicKey = loadPublicKeyFromPem("./public.pem");
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build();
            Jws<Claims> claims = parser.parseClaimsJws(cookieToken);
            String subject = claims.getBody().getSubject();
            System.out.println("JWT validated for subject: " + subject);
            System.out.println("Proceeding to service method"+claims.getBody());
            boolean isPasswordResetToken = claims.getBody().get("password_reset_token", Boolean.class) != null
                    && claims.getBody().get("password_reset_token", Boolean.class);
            if(isPasswordResetToken){
                return Mono.just(ResponseEntity.<T>status(HttpStatus.UNAUTHORIZED).build());
            }
            UserRequest userReq = new UserRequest();
            userReq.setUsername(claims.getBody().get("UserName", String.class));
            return serviceMethod.apply(exchange, userReq)
                    .map(t -> ResponseEntity.ok(t))
                    .defaultIfEmpty(ResponseEntity.<T>status(HttpStatus.UNAUTHORIZED).build());
        } catch (io.jsonwebtoken.JwtException e) {
            System.out.println("JWT validation error: " + e.getMessage());
            return Mono.just(ResponseEntity.<T>status(HttpStatus.UNAUTHORIZED).build());
        } catch (Exception e) {
            System.out.println("JWT processing error: " + e.getMessage());
            return Mono.just(ResponseEntity.<T>status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    // Load an RSA public key from a PEM on the classpath (BEGIN PUBLIC KEY /
    // X.509).
    public static RSAPublicKey loadPublicKeyFromPem(String classpathLocation) {
        try (InputStream is = new ClassPathResource(classpathLocation).getInputStream()) {
            String pem = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            String sanitized = pem
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] keyBytes = Base64.getDecoder().decode(sanitized);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) kf.generatePublic(spec);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load RSA public key from " + classpathLocation, e);
        }
    }
}
