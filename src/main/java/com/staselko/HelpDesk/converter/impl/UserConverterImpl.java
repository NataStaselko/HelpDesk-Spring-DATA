package com.staselko.HelpDesk.converter.impl;

import com.staselko.HelpDesk.converter.UserConverter;
import com.staselko.HelpDesk.model.dto.UserDto;
import com.staselko.HelpDesk.model.entiti.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverterImpl implements UserConverter {
    @Override
    public User toUser(UserDto userDto) {
        User user = new User();
        user.setFirst_name(userDto.getFirst_name());
        user.setLast_name(userDto.getLast_name());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());

        return user;
    }

    @Override
    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirst_name(user.getFirst_name());
        userDto.setLast_name(user.getLast_name());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
