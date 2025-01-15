package com.ecommerce.userservice.dto;


import com.ecommerce.userservice.model.Authority_list;

public class AuthorityResponseDto {
    String name;
    Long id;
    public AuthorityResponseDto(String name, Long id) {
        this.name = name;
        this.id = id;
    }
    AuthorityRequestDto toDto() {
        return new AuthorityRequestDto(Authority_list.valueOf(name), null);
    }

}
