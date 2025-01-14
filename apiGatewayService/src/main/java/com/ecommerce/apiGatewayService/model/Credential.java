package com.ecommerce.apiGatewayService.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credential {
    private String email;
    private String password;

    public Credential() {
    }

    public Credential(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
