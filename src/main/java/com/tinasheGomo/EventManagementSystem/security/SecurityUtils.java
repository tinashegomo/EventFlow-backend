package com.tinasheGomo.EventManagementSystem.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.tinasheGomo.EventManagementSystem.entity.user.UserEntity;

public class SecurityUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static AuthUser getCurrentUser() {
        Authentication auth = getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AuthUser) {
            return (AuthUser) auth.getPrincipal();
        }
        return null;
    }

    public static String getCurrentUserEmail() {
        AuthUser user = getCurrentUser();
        return user != null ? user.getUsername() : null;
    }

    public static String getCurrentUserRole() {
        AuthUser user = getCurrentUser();
        return user != null ? user.getRole() : null;
    }

    public static UserEntity getCurrentUserEntity() {
        AuthUser authUser = getCurrentUser();
        return authUser != null ? authUser.getUser() : null;
    }
}
