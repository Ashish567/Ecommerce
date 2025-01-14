package com.ecommerce.apiGatewayService.utils;

import com.ecommerce.apiGatewayService.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@PropertySource("classpath:application.yaml")
@Component
public class JWTUtil {
    private final Environment env;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.key}")
    private String jwt;

    public JWTUtil(Environment env) {
        this.env = env;
    }

    public Claims getALlClaims(String token) {
        String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token.trim().split(" ")[1].trim())
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return this.getALlClaims(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

}
