package com.example.ourivesaria.repositories.users;

import com.example.ourivesaria.entities.users.UserEntity;
import com.example.ourivesaria.entities.users.UserSignUpToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSignUpTokenRepository extends JpaRepository<UserSignUpToken, Long> {

    Optional<UserSignUpToken> findByUser(UserEntity user);
}