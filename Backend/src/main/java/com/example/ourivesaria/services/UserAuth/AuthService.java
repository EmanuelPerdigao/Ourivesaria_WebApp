package com.example.ourivesaria.services.UserAuth;

import com.example.ourivesaria.configs.jwtConfig.JwtTokenGenerator;
import com.example.ourivesaria.dtos.jwtTokens.JwtTokenDto;
import com.example.ourivesaria.dtos.users.UserDto;
import com.example.ourivesaria.entities.users.UserEntity;
import com.example.ourivesaria.entities.users.UserSignUpToken;
import com.example.ourivesaria.mappers.users.UserMapper;
import com.example.ourivesaria.repositories.RefreshTokens.RefreshTokenRepo;
import com.example.ourivesaria.repositories.users.UserRepository;
import com.example.ourivesaria.enums.TokenTypes;
import com.example.ourivesaria.entities.refreshTokens.RefreshTokenEntity;
import com.example.ourivesaria.responses.ApiResponse;
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
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepo refreshTokenRepo;
    private final UserMapper userMapper;
    private final EmailSendingSignUpConfirmationService emailSendingService;
    private final UserSignUpTokenServiceImpl userSignUpTokenService;

    private final int accessTokenExpiryTimeInMins = 10;
    private final int refreshTokenExpiryTimeInDays = 15;


    // ##############################  getJwtTokensAfterAuthentication  ####################################

    /**
     * This method generates JWT tokens after successful authentication of the user.
     *
     * @param authentication Authentication object that contains the user details
     * @param response       HttpServletResponse that is used to set the refresh token as a cookie
     * @return JwtTokenDto that contains the access token, access token expiry time, username and token type
     */
    public JwtTokenDto getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse response) {
        try {
            //search for the user in the database by the email id and if exists return the user entity
            var userInfoEntity = userRepository.findByEmailId(authentication.getName())
                    .orElseThrow(() -> {
                        log.error("[AuthService:userSignInAuth] User :{} not found", authentication.getName());
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND ");
                    });

            //generate an access token
            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

            //generate a refresh token
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            creatRefreshTokenCookie(response, refreshToken);
            //Let's save the refreshToken as well

            //Save the refresh token in DB
            saveRefreshTokenInDB(userInfoEntity, refreshToken);


            log.info("[AuthService:userSignInAuth] Access token for user:{}, has been generated", userInfoEntity.getUserName());
            return JwtTokenDto.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(15 * 60)
                    .userName(userInfoEntity.getUserName())
                    .tokenTypes(TokenTypes.Bearer)
                    .build();


        } catch (Exception e) {
            log.error("[AuthService:userSignInAuth]Exception while authenticating the user due to :" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }

    }

    //#####################  SAVE REFRESH TOKEN IN DB ASSOCIATED TO THE USER ####################

    private void saveRefreshTokenInDB(UserEntity userEntity, String refreshToken) {

        var refreshTokenEntity = RefreshTokenEntity.builder()
                .user(userEntity)
                .refreshToken(refreshToken)
                .revoked(false)
                .build();
        refreshTokenRepo.save(refreshTokenEntity);

    }


    //#####################  CREATE REFRESH TOKEN AND ADD IT TO THE RESPONSE HTTP SERVLET OBJECT ####################

    private Cookie creatRefreshTokenCookie(HttpServletResponse response, String refreshToken) {

        //Create the cookie with the refresh token string and add it to the response
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setMaxAge(refreshTokenExpiryTimeInDays * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);
        return refreshTokenCookie;

    }


    //#####################  GET ACCESS TOKEN USING REFRESH TOKEN ####################

    /**
     * This method is used to get access token using refresh token if it is valid
     *
     * @param authorizationHeader The authorization header which contains the refresh token
     * @return JwtTokenDto which contains the access token
     */

    public Object getAccessTokenUsingRefreshToken(String authorizationHeader) {

        //Check if the authorizationHeader is valid and contains a Bearer token
        if (authorizationHeader == null || !authorizationHeader.startsWith(TokenTypes.Bearer.name())) {
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide a valid token");
        }

        //Extract the refresh token from the authorization header
        final String refreshTokenFromAthorizationHeader = authorizationHeader.substring(7);

        //Find refreshTokenFromAthorizationHeader from database
        final Optional<RefreshTokenEntity> refreshTokenFromDB = refreshTokenRepo.findByRefreshToken(refreshTokenFromAthorizationHeader);

        // Check if the refresh token exists and is not revoked
        if (refreshTokenFromDB.isEmpty() || refreshTokenFromDB.get().isRevoked()) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Refresh token doesnt exists");
        }

        // Get the user from the refresh token
        UserEntity userEntity = refreshTokenFromDB.get().getUser();

        //Now create the Authentication object
        Authentication authentication = createAuthenticationObject(userEntity);

        //Use the authentication object to generate new accessToken as the Authentication object that we will have may not contain correct role.
        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(5 * 60)
                .userName(userEntity.getUserName())
                .tokenTypes(TokenTypes.Bearer)
                .build();
    }


    //#####################  CREATE AUTHENTICATION OBJECT ####################

    /**
     * Create an Authentication object from the userEntity
     *
     * @param userEntity UserEntity object
     * @return Authentication object
     */
    private static Authentication createAuthenticationObject(UserEntity userEntity) {

        // Extract user details from UserDetailsEntity
        String username = userEntity.getEmailId();
        String password = userEntity.getPassword();
        String roles = userEntity.getRoles().toString();

        // Extract authorities from roles
        String[] roleArray = roles.split(",");
        GrantedAuthority[] authorities = Arrays.stream(roleArray)
                .map(role -> (GrantedAuthority) role::trim)
                .toArray(GrantedAuthority[]::new);

        return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(authorities));
    }


    public ApiResponse registerUser(UserDto userDto, HttpServletResponse httpServletResponse) {
        try {
            log.info("[AuthService:registerUser]User Registration Started with :::{}", userDto.emailId());

            // Check if user already exists in DB by email
            Optional<UserEntity> user = userRepository.findByEmailId(userDto.emailId());

            if (user.isPresent()) {

                log.info("[AuthService:registerUser]User Already Exist :::{}", userDto.emailId());

                // Handle the case where the user already exists
                return handleExistingUser(user.get());

            } else {
                // Register a new user
                return registerNewUser(userDto, httpServletResponse);
            }

        } catch (Exception e) {
            log.error("[AuthService:registerUser]Exception while registering the user due to :" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Handles the existing user registration process.
     *
     * @param user The existing user entity.
     * @throws Exception if the user is already valid.
     */
    private ApiResponse handleExistingUser(UserEntity user) throws Exception {

        if (user.isValid()) {
            // If user is already valid, return an error message
            return new ApiResponse(false, "User Already Exist and is already valid!");
        } else {
            // If user is not valid, send a confirmation email with the existing token
            Optional<UserSignUpToken> userSignUpTokenEntity = userSignUpTokenService.findByUser(user);

            //TODO: generate anoter token if the token is NULL
            if (userSignUpTokenEntity.isPresent() && userSignUpTokenEntity.get().getToken() != null) {
                {
                    emailSendingService.sendSignUpConfirmationEmail(user.getEmailId(),userSignUpTokenEntity.get().getToken());

                    return new ApiResponse(false, "User already exist, another confirmation email has been sent to your email. Please Verify Your Email");
                }
            }else{
                return new ApiResponse(false, "User Already Exist");
            }
        }
    }


    // ##############################  SIGN-UP USER / REGISTER NEW USER  ####################################

    /**
     * Registers a new user and sends a confirmation email.
     *
     * @param userDto             DTO object containing user details.
     * @param httpServletResponse HttpServletResponse to set the cookie.
     * @return JwtTokenDto containing accessToken and name.
     * @throws Exception if there is an error during registration.
     */
    private ApiResponse registerNewUser(UserDto userDto, HttpServletResponse httpServletResponse) throws Exception {

        // Convert UserDto to UserEntity
        UserEntity userEntity = userMapper.convertToEntity(userDto);

        // Save user in DB
        UserEntity savedUser = userRepository.save(userEntity);

        // Generate a signup token and send a confirmation email
        String userSignUpToken = userSignUpTokenService.generateToken();

        // Save the signup token in the database
        UserSignUpToken userSignUpTokenEntity = new UserSignUpToken();
        userSignUpTokenEntity.setUser(savedUser);
        userSignUpTokenEntity.setToken(userSignUpToken);
        userSignUpTokenService.saveToken(userSignUpTokenEntity);

        emailSendingService.sendSignUpConfirmationEmail(userEntity.getEmailId(),userSignUpTokenEntity.getToken());

        return new ApiResponse(true, "User Registered Successfully, please verify your email address");
    }

    /**
     * Generates JWT tokens, saves the refresh token in the database and as a cookie.
     *
     * @param userEntity          The user entity.
     * @param httpServletResponse HttpServletResponse to set the cookie.
     * @return JwtTokenDto containing accessToken and name.
     */
    private JwtTokenDto generateAndSaveJWTTokens(UserEntity userEntity, HttpServletResponse httpServletResponse) {

        // Create an authentication object from the user entity
        Authentication authentication = createAuthenticationObject(userEntity);

        // Generate access and refresh tokens
        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
        String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

        // Save the refresh token in the database
        saveRefreshTokenInDB(userEntity, refreshToken);

        // Create a refresh token cookie and add it to the response
        creatRefreshTokenCookie(httpServletResponse, refreshToken);

        log.info("[AuthService:registerUser] User:{} Successfully registered", userEntity.getUserName());

        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(accessTokenExpiryTimeInMins * 60)
                .userName(userEntity.getUserName())
                .tokenTypes(TokenTypes.Bearer)
                .build();
    }

    public ApiResponse validateUserRegistrationToken(String signUpToken, HttpServletResponse httpServletResponse) {

        //Search for the token in the database and if exists return the token entity
        Optional<UserSignUpToken> userSignUpToken = userSignUpTokenService.findByToken(signUpToken);


        if (userSignUpToken.isPresent()){

            UserEntity user = userSignUpToken.get().getUser();

            // If is not valid ( the email is not confirmed )
            if(!user.isValid()){

                log.info("validate user registration token:{}", user.getEmailId());
                user.setValid(true);
                userRepository.save(user);

                // Generate and save tokens, then return the token DTO
                JwtTokenDto jwtTokenDto = generateAndSaveJWTTokens(user, httpServletResponse);

                return new ApiResponse(true, "User Verified Successfully", jwtTokenDto);

            }else{
                return new ApiResponse(false, "User is already verified!");
            }

        }else {
            return new ApiResponse(false, "User Signup Token Not Found, please try again!");
        }

    }

}
