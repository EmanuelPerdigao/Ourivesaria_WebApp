package com.example.ourivesaria.entities.users;

import com.example.ourivesaria.entities.refreshTokens.RefreshTokenEntity;
import com.example.ourivesaria.enums.UserRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USER_INFO")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(nullable = false, name = "EMAIL_ID", unique = true)
    private String emailId;

    @Column(nullable = false, name = "PASSWORD")
    private String password;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @JsonIgnore
    @Column(nullable = false,name = "IS_VALID")
    private boolean isValid = false;

    @Column(nullable = false, name = "ROLES")
    @Enumerated(EnumType.STRING)
    private UserRoles roles;


    // Many-to-One relationship with RefreshTokenEntity
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RefreshTokenEntity> refreshTokens;

}

