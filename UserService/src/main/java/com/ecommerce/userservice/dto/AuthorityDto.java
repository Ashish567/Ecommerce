package com.ecommerce.userservice.dto;

import com.ecommerce.userservice.model.Authority;
import com.ecommerce.userservice.model.Authority_list;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class AuthorityDto {
    @Enumerated(EnumType.STRING)
    private Authority authority;
    // Add a no-argument constructor
    public AuthorityDto() { }

    // Add a constructor that accepts a string argument
    public AuthorityDto(String authority) { this.authority = Authority.valueOf(authority); }
    public static List<AuthorityDto> from(List<AuthorityRequestDto> authorities) {
        return authorities.stream() .map(authority -> new AuthorityDto(authority.toEntity().getAuthority().name())).collect(Collectors.toList()); }
}
