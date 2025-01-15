package com.ecommerce.userservice.services;

import com.ecommerce.userservice.config.EncoderConfig;
import com.ecommerce.userservice.constants.ApplicationConstants;
import com.ecommerce.userservice.dto.*;
import com.ecommerce.userservice.model.Authority;
import com.ecommerce.userservice.model.Authority_list;
import com.ecommerce.userservice.model.UserEc;
import com.ecommerce.userservice.repository.AuthorityRepo;
import com.ecommerce.userservice.repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@RequiredArgsConstructor
@Service
public class UserService {
    private final AuthorityRepo AuthorityRepo;
    private final UserRepo userRepo;
    private final EncoderConfig passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Environment env;


    @Transactional
    public UserResponseDto signUp(SignUpRequestDto signUpRequest) {
        if (userRepo.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + signUpRequest.getEmail());
        }

        UserEc userEc = new UserEc();
        userEc.setEmail(signUpRequest.getEmail());
        userEc.setUsername(signUpRequest.getUsername());
        userEc.setPassword(passwordEncoder.getBCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        userEc.setRole(signUpRequest.getRoles());
        userEc.setAuthorities(signUpRequest.getAuthorities().stream().map(authority -> new Authority(authority.getAuthority().getAuthority(), userEc)).collect(Collectors.toList()));
        UserEc savedUserEc = userRepo.save(userEc);
        List<Authority> authorities = signUpRequest.getAuthorities().stream()
                .map(authority -> new Authority(authority.getAuthority().getAuthority(),userEc)).toList();
        AuthorityRepo.saveAll(authorities);
        return UserResponseDto.from(savedUserEc);
    }
public LoginResponseDto login(LoginRequestDto loginRequest) {
    String jwt = "";
    Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getEmail(),
            loginRequest.getPassword());
    Authentication authenticationResponse = authenticationManager.authenticate(authentication);
    if(null != authenticationResponse && authenticationResponse.isAuthenticated()) {
        if (null != env) {
            String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                    ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            jwt = Jwts.builder().issuer("Ecommerce").subject("JWT Token")
                    .claim("username", authenticationResponse.getName())
                    .claim("authorities", authenticationResponse.getAuthorities().stream().map(
                            GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .issuedAt(new java.util.Date())
                    .expiration(new java.util.Date(System.currentTimeMillis() + 3600000))
                    .signWith(secretKey).compact();
        }
    }
    return new LoginResponseDto(HttpStatus.OK.getReasonPhrase(), jwt);
    }

    public UserEc getUserDetailsAfterLogin(Authentication authentication) {
        System.out.println("Authentication: " + authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
        Optional<UserEc> optionalCustomer = userRepo.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }

//    private List<GrantedAuthority> getAuthorities(UserEc user) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Authority authority : user.getAuthorities()) {
//            authorities.add(new SimpleGrantedAuthority(authority.getAuthority().name()));
//        }
//        return authorities;
//    }

    public TokenValidateResponseDto validateToken(String jwt) throws  Exception {
        TokenValidateResponseDto tokenValidateResponseDto = new TokenValidateResponseDto();
        if(null != jwt) {
            try {
                    String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                            ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts
                        .parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(jwt.trim().split(" ")[1].trim())
                        .getPayload();
                assert claims != null;
                String email = String.valueOf(claims.get("username"));
                String authorities = String.valueOf(claims.get("authorities"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserEc user = userRepo.findByEmail(email).get();
                tokenValidateResponseDto.setValid(true);
                tokenValidateResponseDto.setEmail(email);
                tokenValidateResponseDto.setUserId(user.getId());
                tokenValidateResponseDto.setUsername(user.getUsername());
                tokenValidateResponseDto.setAuthorities(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
                tokenValidateResponseDto.setRole(user.getRole());
                return tokenValidateResponseDto;

            } catch (Exception exception) {
                throw new BadCredentialsException("Invalid Token received!", exception);
            }
        }
        tokenValidateResponseDto.setValid(false);
        return tokenValidateResponseDto;
    }
}
