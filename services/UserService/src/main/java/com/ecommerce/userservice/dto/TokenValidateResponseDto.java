package com.ecommerce.userservice.dto;

import com.ecommerce.userservice.model.Role_list;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidateResponseDto {
    private boolean isValid;
    private Long userId;
    private String username;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role_list role;
    private List<String> authorities;
}
