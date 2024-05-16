package com.example.ourivesaria.services.UserAuth.SignUpToken;

import com.example.ourivesaria.entities.users.UserEntity;
import com.example.ourivesaria.entities.users.UserSignUpToken;
import com.example.ourivesaria.repositories.users.UserSignUpTokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

@Service
public class UserSignUpTokenServiceImpl implements UserSignUpTokenService{


    private final UserSignUpTokenRepository userSignUpTokenRepository;

    public UserSignUpTokenServiceImpl(UserSignUpTokenRepository userSignUpTokenRepository) {
        this.userSignUpTokenRepository = userSignUpTokenRepository;
    }


    @Override
    public String generateToken(){
        return RandomGenerator.getDefault().nextInt(100000, 999999) + "";
    }

    @Override
    public Optional<UserSignUpToken> findByUser(UserEntity user) {
        return userSignUpTokenRepository.findByUser(user);
    }

    @Override
    public Optional<UserSignUpToken> getById(Long id) {
        return userSignUpTokenRepository.findById(id);
    }

    @Override
    public UserSignUpToken saveToken(UserSignUpToken token) {
        return userSignUpTokenRepository.save(token);
    }

    @Override
    public UserSignUpToken updateToken(UserSignUpToken token) {
        return userSignUpTokenRepository.save(token);
    }

    @Override
    public void deleteToken(Long id) {
        userSignUpTokenRepository.deleteById(id);
    }

    @Override
    public List<UserSignUpToken> listAllTokens() {
        return userSignUpTokenRepository.findAll();
    }


}
