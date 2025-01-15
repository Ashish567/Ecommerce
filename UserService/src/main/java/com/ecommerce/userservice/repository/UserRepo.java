package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.model.UserEc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEc, Long> {
    Optional<UserEc> findByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<UserEc> findByUsername(String username);

    Optional<UserEc> findByEmailOrUsername(String email, String username);
}
