package com.ecommerce.userservice.dto;

import com.ecommerce.userservice.model.Authority;
import com.ecommerce.userservice.model.Authority_list;
import com.ecommerce.userservice.model.UserEc;

public record AuthorityRequestDto(Authority_list authority, UserEc userz) {
    public Authority toEntity() {
        return new Authority(authority,userz);
    }
}
