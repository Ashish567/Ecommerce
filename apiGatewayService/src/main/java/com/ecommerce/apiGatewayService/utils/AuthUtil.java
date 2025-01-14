package com.ecommerce.apiGatewayService.utils;

import com.ecommerce.apiGatewayService.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthUtil {
    private final RestTemplate restTemplate;

    public AuthUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getToken(String email, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Credential> request = new HttpEntity<>(
                new Credential(email, password), headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8088/login", HttpMethod.POST, request, String.class);
        System.out.println("token:" + response.getBody());
        return response.getBody();
    }
}
