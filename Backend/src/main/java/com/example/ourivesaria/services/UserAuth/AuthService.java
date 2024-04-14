package com.example.ourivesaria.services.UserAuth;

import com.example.ourivesaria.configs.jwtConfig.JwtTokenGenerator;
import com.example.ourivesaria.dtos.jwtTokens.JwtTokenDto;
import com.example.ourivesaria.dtos.users.UserDto;
import com.example.ourivesaria.entities.users.UserEntity;
import com.example.ourivesaria.mappers.users.UserMapper;
import com.example.ourivesaria.repositories.RefreshTokens.RefreshTokenRepo;
import com.example.ourivesaria.repositories.users.UserRepository;
import com.example.ourivesaria.enums.TokenTypes;
import com.example.ourivesaria.entities.refreshTokens.RefreshTokenEntity;
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
    private final int accessTokenExpiryTimeInMins = 10;
    private final int refreshTokenExpiryTimeInDays = 15;


    // ##############################  getJwtTokensAfterAuthentication  ####################################
    /**
     * This method generates JWT tokens after successful authentication of the user.
     * @param authentication Authentication object that contains the user details
     * @param response HttpServletResponse that is used to set the refresh token as a cookie
     * @return JwtTokenDto that contains the access token, access token expiry time, username and token type
     */
    public JwtTokenDto getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse response) {
        try
        {
            //search for the user in the database by the email id and if exists return the user entity
            var userInfoEntity = userRepository.findByEmailId(authentication.getName())
                    .orElseThrow(()->{
                        log.error("[AuthService:userSignInAuth] User :{} not found",authentication.getName());
                        return new ResponseStatusException(HttpStatus.NOT_FOUND,"USER NOT FOUND ");});

            //generate an access token
            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

            //generate a refresh token
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            creatRefreshTokenCookie(response,refreshToken);
            //Let's save the refreshToken as well

            //Save the refresh token in DB
            saveRefreshTokenInDB(userInfoEntity,refreshToken);


            log.info("[AuthService:userSignInAuth] Access token for user:{}, has been generated",userInfoEntity.getUserName());
            return  JwtTokenDto.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(15 * 60)
                    .userName(userInfoEntity.getUserName())
                    .tokenTypes(TokenTypes.Bearer)
                    .build();


        }catch (Exception e){
            log.error("[AuthService:userSignInAuth]Exception while authenticating the user due to :"+e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Please Try Again");
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
        Cookie refreshTokenCookie = new Cookie("refresh_token",refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setMaxAge(refreshTokenExpiryTimeInDays * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);
        return refreshTokenCookie;

    }



    //#####################  GET ACCESS TOKEN USING REFRESH TOKEN ####################
    /**
     * This method is used to get access token using refresh token if it is valid
     * @param authorizationHeader The authorization header which contains the refresh token
     * @return JwtTokenDto which contains the access token
     */

    public Object getAccessTokenUsingRefreshToken(String authorizationHeader) {

        //Check if the authorizationHeader is valid and contains a Bearer token
        if(authorizationHeader == null || !authorizationHeader.startsWith(TokenTypes.Bearer.name())){
            return new ResponseStatusException(HttpStatus.BAD_REQUEST,"Please provide a valid token");
        }

        //Extract the refresh token from the authorization header
        final String refreshTokenFromAthorizationHeader = authorizationHeader.substring(7);

        //Find refreshTokenFromAthorizationHeader from database
        final Optional<RefreshTokenEntity> refreshTokenFromDB = refreshTokenRepo.findByRefreshToken(refreshTokenFromAthorizationHeader);

        // Check if the refresh token exists and is not revoked
        if (refreshTokenFromDB.isEmpty() || refreshTokenFromDB.get().isRevoked()){
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Refresh token doesnt exists");
        }

        // Get the user from the refresh token
        UserEntity userEntity = refreshTokenFromDB.get().getUser();

        //Now create the Authentication object
        Authentication authentication =  createAuthenticationObject(userEntity);

        //Use the authentication object to generate new accessToken as the Authentication object that we will have may not contain correct role.
        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        return  JwtTokenDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(5 * 60)
                .userName(userEntity.getUserName())
                .tokenTypes(TokenTypes.Bearer)
                .build();
    }


    //#####################  CREATE AUTHENTICATION OBJECT ####################
    /**
     * Create an Authentication object from the userEntity
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


    // ##############################  SIGN-UP USER / REGISTER NEW USER  ####################################
    /**
     * This method is used to register a new user by creating a new UserEntity in the database
     * @param userDto : DTO object containing user details
     * @param httpServletResponse : HttpServletResponse to set the cookie
     * @return : JwtTokenDto containing accessToken and userName
     */
    public JwtTokenDto registerUser(UserDto userDto, HttpServletResponse httpServletResponse){

        try{
            log.info("[AuthService:registerUser]User Registration Started with :::{}", userDto);

            // Check if user already exists in DB by email
            Optional<UserEntity> user = userRepository.findByEmailId(userDto.userEmail());

            // Throw an exception if user already exists
            if(user.isPresent()){
                throw new Exception("User Already Exist");
            }

            // Convert UserDto to UserEntity
            UserEntity userEntity = userMapper.convertToEntity(userDto);

            // Create Autentication Object from UserEntity
            Authentication authentication = createAuthenticationObject(userEntity);

            // Generate a JWT token
            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

            // Generate a Refresh Token
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            // Save user in DB
            UserEntity savedUser = userRepository.save(userEntity);

            // Save the refresh token in DB
            saveRefreshTokenInDB(userEntity,refreshToken);

            // Create refresh token cookie and add it to the response http servlet object
            creatRefreshTokenCookie(httpServletResponse,refreshToken);

            log.info("[AuthService:registerUser] User:{} Successfully registered",savedUser.getUserName());

            // Return JwtTokenDto with the access token
            return   JwtTokenDto.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(accessTokenExpiryTimeInMins * 60)
                    .userName(savedUser.getUserName())
                    .tokenTypes(TokenTypes.Bearer)
                    .build();

        }catch (Exception e){
            log.error("[AuthService:registerUser]Exception while registering the user due to :"+e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }

    }
}
