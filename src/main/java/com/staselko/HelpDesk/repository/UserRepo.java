package com.staselko.HelpDesk.repository;

import com.staselko.HelpDesk.model.entiti.User;
import com.staselko.HelpDesk.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByRole(Role role);


}
