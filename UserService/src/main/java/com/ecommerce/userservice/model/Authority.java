package com.ecommerce.userservice.model;

import com.ecommerce.userservice.dto.AuthorityResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="authorities")
public class Authority extends BaseModel {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Authority_list authority;
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEc userz;

    public Authority(Authority_list authority, UserEc userz) {
        this.authority = authority;
        this.userz = userz;
    }

    public static Authority valueOf(String authority) {
        return new Authority(Authority_list.valueOf(authority), null);
    }

    public AuthorityResponseDto toDto() {
        return new AuthorityResponseDto(authority.name(), id);
    }
}