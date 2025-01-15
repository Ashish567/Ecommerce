package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.AuthorityRequestDto;
import com.ecommerce.userservice.dto.AuthorityResponseDto;
import com.ecommerce.userservice.services.AuthorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authority")
public class AuthorityController {
    private final AuthorityService authorityService;
    AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }
    @PostMapping
    public ResponseEntity<AuthorityResponseDto> createAuthority(@RequestBody AuthorityRequestDto authorityRequestDto) {
        return new ResponseEntity<>(authorityService.createAuthority(authorityRequestDto), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<AuthorityResponseDto>> getAuthority() {
        return new ResponseEntity<>(authorityService.getAuthority(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AuthorityResponseDto> getAuthorityById(@PathVariable Long id) {
        return new ResponseEntity<>(authorityService.getAuthorityById(id), HttpStatus.OK);
    }
}
