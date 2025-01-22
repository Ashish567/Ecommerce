package com.ecommerce.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "USERSZ")
public class UserEc extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private Long id;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String username;
    private boolean isEmailVerified;
    @Enumerated(EnumType.STRING)
    private Role_list role;

    @Override
    public String toString() {
        return "UserEc{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }

    @OneToMany(mappedBy = "userz", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Authority> authorities;
}
