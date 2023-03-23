package com.staselko.HelpDesk.service.Impl;

import com.staselko.HelpDesk.model.entity.User;
import com.staselko.HelpDesk.model.enums.Role;
import com.staselko.HelpDesk.model.exception.ResourceNotFoundException;
import com.staselko.HelpDesk.repository.UserRepo;
import com.staselko.HelpDesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public List<User> getAllUsersByRole(Role role) {
        return userRepo.findAllByRole(role);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }
}
