package com.staselko.HelpDesk.service;

import com.staselko.HelpDesk.model.entiti.User;
import com.staselko.HelpDesk.model.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsersByRole(Role role);
    User getUserById(Long userId);
}
