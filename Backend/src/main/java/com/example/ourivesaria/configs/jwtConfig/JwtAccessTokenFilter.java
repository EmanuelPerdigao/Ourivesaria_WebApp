package com.example.ourivesaria.configs.jwtConfig;

import com.example.ourivesaria.configs.RSAKeyRecord;
import com.example.ourivesaria.enums.TokenTypes;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAccessTokenFilter extends OncePerRequestFilter {

    private final RSAKeyRecord rsaKeyRecord;
    private final JwtTokenUtils jwtTokenUtils;


    /**
     * Filters HTTP requests using JWT access tokens. It checks for the presence of an authorization header and validates the token. If the token is invalid or expired, it sends a 401 Unauthorized response. If the token is valid, it creates an authentication token and sets it in the security context. Finally, it allows the filter chain to proceed.
     *
     * @param  request       the HTTP servlet request
     * @param  response      the HTTP servlet response
     * @param  filterChain   the filter chain for the request
     * @throws ServletException   if the servlet request encounters an error
     * @throws IOException        if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {

        try{
            log.info("[JwtAccessTokenFilter:doFilterInternal] :: Started ");
            log.info("[JwtAccessTokenFilter:doFilterInternal]Filtering the Http Request:{}",request.getRequestURI());

            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            // Try to validate AuthHeader if it isn't valid or expired return 401
            if (!isAuthHeaderValid(authHeader, request, response, filterChain)) {
                return;
            }

            // Try to decode the token if it isn't valid or expired return 401
            Jwt jwtToken = jwtTokenUtils.decodeJwtToken(rsaKeyRecord, response, authHeader);

            if(jwtToken == null){
                return;
            }

            log.info("[JwtAccessTokenFilter:doFilterInternal] Token is valid and is decoded");

            final String userName = jwtTokenUtils.getUserName(jwtToken);

            if(!userName.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null){

                UserDetails userDetails = jwtTokenUtils.userDetails(userName);

                if(jwtTokenUtils.isTokenValid(jwtToken,userDetails)){

                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken createdToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    createdToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(createdToken);
                    SecurityContextHolder.setContext(securityContext);
                }
                else {

                    // No authorization header provided, handle accordingly
                    log.warn("[JwtAccessTokenFilter:doFilterInternal] Token expired.");

                    // Set the HTTP status code to 401 Unauthorized
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                    // Add a custom error message to the response
                    response.getWriter().write("Token invalid request another token or login again!");

                    return;
                }

            }
            log.info("[JwtAccessTokenFilter:doFilterInternal] Completed");

            filterChain.doFilter(request,response);

        }catch (JwtValidationException jwtValidationException){
            log.error("[JwtAccessTokenFilter:doFilterInternal] Exception due to :{}",jwtValidationException.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,jwtValidationException.getMessage());
        }

    }

    private boolean isAuthHeaderValid(String authHeader, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        //############################     Check if the Authorization header is present and valid ############################
        if(authHeader == null || !authHeader.startsWith(TokenTypes.Bearer.name())) {
            log.warn("[JwtAccessTokenFilter:doFilterInternal] No Authorization header provided.");

            // Set the HTTP status code to 401 Unauthorized
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            // Add a custom error message to the response
            response.getWriter().write("No authorization provided. Please log in.");

            return false;
        }
        return true;
    }
}