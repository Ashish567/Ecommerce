package com.ecommerce.userservice.config;

import com.ecommerce.userservice.model.UserEc;
import com.ecommerce.userservice.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EcommerceUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    public EcommerceUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEc users = userRepo.findByEmail(username).orElseThrow(() -> new
                UsernameNotFoundException("User details not found for the user: " + username));
        List<GrantedAuthority> authorities = users.getAuthorities().stream().map(authority -> new
                SimpleGrantedAuthority(authority.getAuthority().name())).collect(Collectors.toList());
        return new User(users.getEmail(), users.getPassword(), authorities);
    }
}
