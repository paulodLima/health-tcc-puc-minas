package com.reimbursement.health.config.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.UUID;

public class AuthenticationUtil {
    public static String getLogin() {
        var principal = getPrincipal();
        return principal.getClaim("preferred_username");
    }

    public static UUID getId() {
        var principal = getPrincipal();
        return UUID.fromString(principal.getClaim("sub"));
    }

    private static Jwt getPrincipal() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new RuntimeException("No authentication found");
        }
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt jwt) {
            return jwt;
        }
        throw new RuntimeException("Authentication is not a JWT");
    }
}
