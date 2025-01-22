package com.ecommerce.userservice.dto;

import com.ecommerce.userservice.model.UserEc;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserResponseDto {
    private String username;
    private String email;
    private String role;
    private List<AuthorityDto> authorities;

    public static UserResponseDto from(UserEc userEc) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setEmail(userEc.getEmail());
        responseDto.setUsername(userEc.getUsername());
        responseDto.setRole(userEc.getRole().name());
        responseDto.setAuthorities(userEc.getAuthorities().stream()
                .map(authority -> new AuthorityDto(authority.getAuthority().name()))
                .collect(java.util.stream.Collectors.toList()));
        return responseDto;
    }
}