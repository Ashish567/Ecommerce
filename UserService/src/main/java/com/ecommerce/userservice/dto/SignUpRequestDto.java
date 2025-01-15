package com.ecommerce.userservice.dto;

import com.ecommerce.userservice.model.Role_list;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.ecommerce.userservice.model.Authority;

import java.util.Set;

@Getter
@Setter
@ToString
public class SignUpRequestDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private boolean isEmailVerified;
    @NotEmpty(message = "Roles are required")
    @Enumerated(EnumType.STRING)
    private Role_list roles;
    private Set<AuthorityDto> authorities;
}