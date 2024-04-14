package com.example.ourivesaria.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserDto(

        @NotEmpty(message = "User Name must not be empty")
        String userName,

        @NotEmpty(message = "User Mobile num must not be empty")
        String userMobileNo,

        @NotEmpty(message = "User email must not be empty")
        @Email(message = "Invalid email format")
        String userEmail,

        @NotEmpty(message = "User password must not be empty")
        String userPassword
){

}
