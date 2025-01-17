package com.ecommerce.apiGatewayService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final Environment env;
    @Value("${jwt.secretkey}")
    private String JWT_SECRET_KEY;
    @Value("${jwt.secretval}")
    private String JWT_SECRET_DEFAULT_VALUE;

    public Claims validateToken(final String token) {
        String secret = env.getProperty(JWT_SECRET_KEY,
                JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
