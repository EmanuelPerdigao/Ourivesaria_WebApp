package com.example.ourivesaria.entities.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USER_SIGNUP_TOKEN")
public class UserSignUpToken {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "TOKEN", unique = true)
    private String token;

    @Column(nullable = false, name = "EXPIRY_DATE")
    private Date expiryDate = new Date(System.currentTimeMillis() + 1000 * 60 * 30);

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;



}
