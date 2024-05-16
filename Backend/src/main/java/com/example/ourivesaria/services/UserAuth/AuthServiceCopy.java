package com.example.ourivesaria.services.UserAuth;

import com.example.ourivesaria.configs.jwtConfig.JwtTokenGenerator;
import com.example.ourivesaria.dtos.jwtTokens.JwtTokenDto;
import com.example.ourivesaria.dtos.users.UserDto;
import com.example.ourivesaria.entities.refreshTokens.RefreshTokenEntity;
import com.example.ourivesaria.entities.users.UserEntity;
import com.example.ourivesaria.entities.users.UserSignUpToken;
import com.example.ourivesaria.enums.TokenTypes;
import com.example.ourivesaria.mappers.users.UserMapper;
import com.example.ourivesaria.repositories.RefreshTokens.RefreshTokenRepo;
import com.example.ourivesaria.repositories.users.UserRepository;
import com.example.ourivesaria.services.UserAuth.SignUpToken.UserSignUpTokenServiceImpl;
import com.example.ourivesaria.services.email.EmailSendingSignUpConfirmationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceCopy {

    private final UserRepository userRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepo refreshTokenRepo;
    private final UserMapper userMapper;
    private final EmailSendingSignUpConfirmationService emailSendingService;
    private final UserSignUpTokenServiceImpl userSignUpTokenService;

    // Expiry time for access token and refresh token
    private final int accessTokenExpiryTimeInMins = 10;
    private final int refreshTokenExpiryTimeInDays = 15;

    /**
     * Registers a new user or handles existing users.
     * @param userDto DTO object containing user details.
     * @param httpServletResponse HttpServletResponse to set the cookie.
     * @return JwtTokenDto containing accessToken and name.
     */
    public JwtTokenDto registerUser(UserDto userDto, HttpServletResponse httpServletResponse) {
        try {
            log.info("[AuthService:registerUser]User Registration Started with :::{}", userDto);

            // Check if user already exists in DB by email
            Optional<UserEntity> user = userRepository.findByEmailId(userDto.emailId());
            if (user.isPresent()) {
                // Handle the case where the user already exists
                handleExistingUser(user.get());
            } else {
                // Register a new user
                return registerNewUser(userDto, httpServletResponse);
            }

        } catch (Exception e) {
            log.error("[AuthService:registerUser]Exception while registering the user due to :" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return null;
    }

    /**
     * Handles the existing user registration process.
     * @param user The existing user entity.
     * @throws Exception if the user is already valid.
     */
    private void handleExistingUser(UserEntity user) throws Exception {
        if (user.isValid()) {
            throw new Exception("User Already Exist");
        } else {
            // If user is not valid, send a confirmation email with the existing token
            Optional<UserSignUpToken> userSignUpTokenEntity = userSignUpTokenService.findByUser(user);
            userSignUpTokenEntity.ifPresent(token ->
                    emailSendingService.sendEmail(user.getEmailId(), "Confirmation Email",
                            "Welcome to Ourivesaria \n Please verify your email by clicking on the link below \n http://localhost:3000/user/verify/" + token.getToken())
            );
        }
    }

    /**
     * Registers a new user and sends a confirmation email.
     * @param userDto DTO object containing user details.
     * @param httpServletResponse HttpServletResponse to set the cookie.
     * @return JwtTokenDto containing accessToken and name.
     * @throws Exception if there is an error during registration.
     */
    private JwtTokenDto registerNewUser(UserDto userDto, HttpServletResponse httpServletResponse) throws Exception {

        // Convert UserDto to UserEntity
        UserEntity userEntity = userMapper.convertToEntity(userDto);

        // Save user in DB
        UserEntity savedUser = userRepository.save(userEntity);

        // Generate a signup token and send a confirmation email
        String userSignUpToken = userSignUpTokenService.generateToken();

        emailSendingService.sendEmail(userEntity.getEmailId(), "Confirmation Email",
                "Welcome to Ourivesaria \n Please verify your email by clicking on the link below \n http://localhost:3000/user/verify/" + userSignUpToken);

        // Save the signup token in the database
        UserSignUpToken userSignUpTokenEntity = new UserSignUpToken();
        userSignUpTokenEntity.setUser(savedUser);
        userSignUpTokenEntity.setToken(userSignUpToken);
        userSignUpTokenService.saveToken(userSignUpTokenEntity);

        // Generate and save tokens, then return the token DTO
        return generateAndSaveTokens(savedUser, httpServletResponse);
    }

    /**
     * Generates JWT tokens, saves the refresh token in the database and as a cookie.
     * @param userEntity The user entity.
     * @param httpServletResponse HttpServletResponse to set the cookie.
     * @return JwtTokenDto containing accessToken and name.
     */
    private JwtTokenDto generateAndSaveTokens(UserEntity userEntity, HttpServletResponse httpServletResponse) {
        // Create an authentication object from the user entity
        Authentication authentication = createAuthenticationObject(userEntity);
        // Generate access and refresh tokens
        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
        String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

        // Save the refresh token in the database
        saveRefreshTokenInDB(userEntity, refreshToken);
        // Create a refresh token cookie and add it to the response
        createRefreshTokenCookie(httpServletResponse, refreshToken);

        log.info("[AuthService:registerUser] User:{} Successfully registered", userEntity.getUserName());

        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(accessTokenExpiryTimeInMins * 60)
                .userName(userEntity.getUserName())
                .tokenTypes(TokenTypes.Bearer)
                .build();
    }

    /**
     * Saves the refresh token in the database.
     * @param userEntity The user entity.
     * @param refreshToken The refresh token.
     */
    private void saveRefreshTokenInDB(UserEntity userEntity, String refreshToken) {
        var refreshTokenEntity = RefreshTokenEntity.builder()
                .user(userEntity)
                .refreshToken(refreshToken)
                .revoked(false)
                .build();
        refreshTokenRepo.save(refreshTokenEntity);
    }

    /**
     * Creates a refresh token cookie and adds it to the response.
     * @param response HttpServletResponse to set the cookie.
     * @param refreshToken The refresh token.
     */
    private void createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setMaxAge(refreshTokenExpiryTimeInDays * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);
    }

    /**
     * Creates an Authentication object from the user entity.
     * @param userEntity The user entity.
     * @return Authentication object.
     */
    private static Authentication createAuthenticationObject(UserEntity userEntity) {
        String username = userEntity.getEmailId();
        String password = userEntity.getPassword();
        String roles = userEntity.getRoles().toString();

        String[] roleArray = roles.split(",");
        GrantedAuthority[] authorities = Arrays.stream(roleArray)
                .map(role -> (GrantedAuthority) role::trim)
                .toArray(GrantedAuthority[]::new);
        return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(authorities));
    }

    // Other existing methods in AuthService...
}

