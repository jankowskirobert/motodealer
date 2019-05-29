package com.jvmless.shop.usermanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class User {
    private UserId userId;
    private Set<UserRole> roles;

    public boolean hasRole(UserRole role) {
        return roles.contains(role);
    }
}
