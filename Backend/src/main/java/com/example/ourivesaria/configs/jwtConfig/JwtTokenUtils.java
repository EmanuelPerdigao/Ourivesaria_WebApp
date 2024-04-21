package com.example.ourivesaria.configs.jwtConfig;

import com.example.ourivesaria.configs.userConfig.UserInfoConfig;
import com.example.ourivesaria.repositories.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    public String getUserName(Jwt jwtToken){
        return jwtToken.getSubject();
    }

    public boolean isTokenValid(Jwt jwtToken, UserDetails userDetails) {

        final String userName = getUserName(jwtToken);
        boolean isTokenExpired = getIfTokenIsExpired(jwtToken);
        boolean isTokenUserSameAsDatabase = userName.equals(userDetails.getUsername());
        boolean hasRequiredScopes = hasRequiredScopes(jwtToken, userDetails);

        return !isTokenExpired && isTokenUserSameAsDatabase && hasRequiredScopes;
    }

    private boolean getIfTokenIsExpired(Jwt jwtToken) {
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
}
