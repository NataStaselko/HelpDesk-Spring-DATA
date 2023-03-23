package com.staselko.HelpDesk.converter;

import com.staselko.HelpDesk.model.dto.UserDto;
import com.staselko.HelpDesk.model.entity.User;

public interface UserConverter {
    User toUser (UserDto userDto);
    UserDto toUserDto(User user);
}
