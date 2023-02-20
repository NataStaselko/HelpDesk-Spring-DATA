package com.staselko.HelpDesk.utils;

import com.staselko.HelpDesk.model.entiti.User;
import com.staselko.HelpDesk.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserProvider {

    public User getCurrentUser() {
        return ((UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
    }
}
