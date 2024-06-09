package com.example.ourivesaria.controllers.userAuth;

import com.example.ourivesaria.dtos.jwtTokens.JwtTokenDto;
import com.example.ourivesaria.dtos.users.UserDto;
import com.example.ourivesaria.entities.products.ProductEntity;
import com.example.ourivesaria.responses.ApiResponse;
import com.example.ourivesaria.services.UserAuth.AuthService;
import com.example.ourivesaria.services.email.EmailSendingService;
import com.example.ourivesaria.services.email.EmailSendingSignUpConfirmationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/auth")
public class UserAuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailSendingSignUpConfirmationService emailSendingService;



    // This is for verify the token that is inside the localstorage of the user and confirm if its valid or not
    //#############################  USER TOKEN VALIDATION  ####################################
    @GetMapping("/token/validate")
    public ResponseEntity<?> validateToken() {

        return ResponseEntity.ok("Token is valid");
    }


    //#############################  USER Sign-Up TOKEN VALIDATION  ####################################
    //                            This is to validate the sign-up token
    @GetMapping("/UserSignUpToken/validate/{token}")
    public ResponseEntity<?> validateUserSignUpToken(@PathVariable String token, HttpServletResponse httpServletResponse) {

        ApiResponse response = authService.validateUserRegistrationToken(token, httpServletResponse);

        if(!response.getSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }else{
            return ResponseEntity.ok(response);
        }

    }


    //#############################  SIGN-UP USER / REGISTER NEW USER  ####################################
    @PostMapping("/sign-up")
    public ResponseEntity<?>registerUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult, HttpServletResponse httpServletResponse){


        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            // Get the error field and error message
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {

            ApiResponse response = authService.registerUser(userDto,httpServletResponse);

            if(!response.getSuccess()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

            }else {
                return ResponseEntity.ok(response);
            }

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }



    //#############################  LOGIN USER  ####################################
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(Authentication authentication, HttpServletResponse response){

        return ResponseEntity.ok(authService.getJwtTokensAfterAuthentication(authentication,response));
    }



    //#############################  GET REFRESH TOKEN USER  ####################################
    @PreAuthorize("hasAuthority('SCOPE_REFRESH_TOKEN')")
    @PostMapping ("/refresh-token")
    public ResponseEntity<?> getAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        return ResponseEntity.ok(authService.getAccessTokenUsingRefreshToken(authorizationHeader));
    }

    //  TODO  Implement refresh token method working with http only cookie
    // #############################  Tests to implement refresh token  ####################################
   /*@PreAuthorize("hasAuthority('SCOPE_REFRESH_TOKEN')")
   @PostMapping ("/refresh-token/test")
   @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<?> getAccessTokenTest(@CookieValue(name = "refresh_token", required = false) String refreshToken){

        System.out.println(refreshToken);
        return ResponseEntity.ok("ok");
    }

    @GetMapping ("/test")
    public ResponseEntity<?> getAccessTokenTest(@CookieValue(name = "refresh_token", required = false) String refreshToken){

        System.out.println(refreshToken);
        return ResponseEntity.ok("ok");
    }


    @GetMapping("/cookie")
    public ResponseEntity<?> getCookie(){

        ResponseCookie cookie = ResponseCookie.from("refresh_token")
                .value("test")
                .domain("localhost") // visible to localhost
                .maxAge(Duration.of(15 * 24 * 60 * 60, ChronoUnit.SECONDS)) // expires in 60 seconds
                .httpOnly(true) // not accessible via JavaScript
                .secure(false) // only sent over HTTPS - except for localhost
                .path("/") // available in all paths
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();

    }
*/

}