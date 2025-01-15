package com.ecommerce.userservice.services;

import com.ecommerce.userservice.dto.AuthorityRequestDto;
import com.ecommerce.userservice.dto.AuthorityResponseDto;
import com.ecommerce.userservice.repository.AuthorityRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorityService {
    private final AuthorityRepo authorityRepo;
    AuthorityService(AuthorityRepo authorityRepo) {
        this.authorityRepo = authorityRepo;
    }

    public AuthorityResponseDto createAuthority(AuthorityRequestDto authorityRequestDto) {
        return authorityRepo.save(authorityRequestDto.toEntity()).toDto();
    }
    public List<AuthorityResponseDto> getAuthority() {
        return authorityRepo.findAll().stream().map(authority -> new AuthorityResponseDto(authority.getAuthority().name(), authority.getId())).collect(Collectors.toList());
    }
    public AuthorityResponseDto getAuthorityById(Long id) {
        return authorityRepo.findById(id).map(authority -> new AuthorityResponseDto(authority.getUserz().getUsername(), authority.getId())).orElse(null);
    }
}
