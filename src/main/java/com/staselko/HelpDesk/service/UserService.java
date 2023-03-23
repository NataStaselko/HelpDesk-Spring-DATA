package com.staselko.HelpDesk.service;

import com.staselko.HelpDesk.model.entity.User;
import com.staselko.HelpDesk.model.enums.Role;

import java.util.List;

public interface UserService {
    List<User> getAllUsersByRole(Role role);
    User getUserById(Long userId);
}
