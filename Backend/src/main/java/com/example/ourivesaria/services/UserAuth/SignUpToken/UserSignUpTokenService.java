package com.example.ourivesaria.services.UserAuth.SignUpToken;

import com.example.ourivesaria.entities.users.UserEntity;
import com.example.ourivesaria.entities.users.UserSignUpToken;

import java.util.List;
import java.util.Optional;

public interface UserSignUpTokenService {

    /**
     * Gets the UserSignUpTokenEntity with the given id
     *
     * @param id the UserSignUpTokenEntity id
     * @return the UserSignUpTokenEntity
     */
    Optional<UserSignUpToken> getById(Long id);

    /**
     * Saves a UserSignUpTokenEntity
     *
     * @param token the UserSignUpTokenEntity to save
     * @return the saved UserSignUpTokenEntity
     */
    UserSignUpToken saveToken(UserSignUpToken token);

    /**
     * Update a UserSignUpTokenEntity
     *
     * @param token the UserSignUpTokenEntity to update
     * @return the updated UserSignUpTokenEntity
     */
    UserSignUpToken updateToken(UserSignUpToken token);

    /**
     * Deletes a UserSignUpTokenEntity
     *
     * @param id the UserSignUpTokenEntity id
     */
    void deleteToken(Long id);

    /**
     * Gets a list of all UserSignUpTokenEntity
     *
     * @return the list of UserSignUpTokenEntity
     */
    List<UserSignUpToken> listAllTokens();

    String generateToken();


    Optional<UserSignUpToken> findByUser(UserEntity user);

    Optional<UserSignUpToken> findByToken(String token);

}
