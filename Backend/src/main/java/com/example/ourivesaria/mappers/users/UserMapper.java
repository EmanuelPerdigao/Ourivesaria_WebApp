package com.example.ourivesaria.mappers.users;

import com.example.ourivesaria.dtos.users.UserDto;
import com.example.ourivesaria.entities.users.UserEntity;
import com.example.ourivesaria.enums.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserEntity convertToEntity(UserDto userDto) {

        UserEntity userEntity = new UserEntity();

        userEntity.setUserName(userDto.userName());
        userEntity.setEmailId(userDto.emailId());
        userEntity.setMobileNumber(userDto.mobileNumber());

        //all new users are created with user role
        userEntity.setRoles(UserRoles.ROLE_USER);

        //all new users are block by default until they confirm email
        userEntity.setValid(false);

        userEntity.setPassword(passwordEncoder.encode(userDto.password()));

        return userEntity;

    }
}
