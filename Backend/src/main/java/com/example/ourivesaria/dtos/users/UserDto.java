package com.example.ourivesaria.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.*;


public record UserDto(

        @NotEmpty(message = "Name must not be empty")
        @Size(min = 6, max = 15, message = "Username must be more than 5 characters and less than 16")
        @Pattern.List({
                @Pattern(regexp = "^\\S*$", message = "Username cannot contain any whitespaces and special characters"),
                @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Username cannot contain any whitespaces and special characters")
        })
        String userName,

        @NotEmpty(message = "Mobile number must not be empty")
        @Size(min = 9, max = 9, message = "Please enter a correct phone number")
        @Pattern(regexp = "^[0-9]*$", message = "Phone number must only be digits")
        String mobileNumber,

        @NotEmpty(message = "Email must not be empty")
        @Email(message = "Invalid email format")
        String emailId,

        @NotEmpty(message = "Password must not be empty")
        String password
){

}
