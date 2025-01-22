package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.model.Authority;
import com.ecommerce.userservice.model.UserEc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, Long> {
    public List<Authority> findByUserz(UserEc user);
}
