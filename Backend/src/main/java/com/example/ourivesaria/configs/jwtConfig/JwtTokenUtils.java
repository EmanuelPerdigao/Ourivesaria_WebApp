package com.example.ourivesaria.configs.jwtConfig;

import com.example.ourivesaria.configs.RSAKeyRecord;
import com.example.ourivesaria.configs.userConfig.UserInfoConfig;
import com.example.ourivesaria.repositories.users.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    public String getUserName(Jwt jwtToken){
        return jwtToken.getSubject();
    }

    public boolean isTokenValid(Jwt jwtToken, UserDetails userDetails) {

        final String userName = getUserName(jwtToken);
        boolean isTokenExpired = isTokenExpired(jwtToken);
        boolean isTokenUserSameAsDatabase = userName.equals(userDetails.getUsername());
        boolean hasRequiredScopes = hasRequiredScopes(jwtToken, userDetails);

        return !isTokenExpired && isTokenUserSameAsDatabase && hasRequiredScopes;
    }

    private boolean isTokenExpired(Jwt jwtToken) {
        return Objects.requireNonNull(jwtToken.getExpiresAt()).isBefore(Instant.now());
    }


    //Verifica se as scope permissions que estao dentro do token realmente são verdadeiras e foram atribuidas ao user a ser autenticado e estao guardadas na db
    private boolean hasRequiredScopes(Jwt jwtToken, UserDetails userDetails) {
        // Extract scopes from the token
        Set<String> tokenScopes = jwtToken.getClaimAsStringList("scope")
                .stream()
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

        // Extract authorities from user details (roles)
        Set<String> userAuthorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(String::toUpperCase)
                .map(authority -> authority.replaceFirst("ROLE_", ""))
                .collect(Collectors.toSet());

        // Check if the user has the required scopes
        return tokenScopes.containsAll(userAuthorities);
    }

    private final UserRepository userRepository;

    public UserDetails userDetails(String emailId){
        return userRepository
                .findByEmailId(emailId)
                .map(UserInfoConfig::new)
                .orElseThrow(()-> new UsernameNotFoundException("UserEmail: "+emailId+" does not exist"));
    }


    public Jwt decodeJwtToken(RSAKeyRecord rsaKeyRecord, HttpServletResponse response, String authHeader) throws IOException {

        String token = "";
        Jwt jwtToken = null;

        //############################     Try to decode the token ############################
        try{
            JwtDecoder jwtDecoder =  NimbusJwtDecoder.withPublicKey(rsaKeyRecord.rsaPublicKey()).build();

            token = authHeader.substring(7);
            jwtToken = jwtDecoder.decode(token);

        }catch (Exception exception){

            log.error("[JwtAccessTokenFilter:doFilterInternal] Exception due to :{}",exception.getMessage());

            // Set the HTTP status code to 401 Unauthorized
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            // Add a custom error message to the response
            response.getWriter().write("Token invalid request another token or login again!");

            log.error("Token is not decode successfully");

            return null;
        }

        log.info("Token is decode successfully: " + jwtToken.getSubject());

        return jwtToken;
    }
}
