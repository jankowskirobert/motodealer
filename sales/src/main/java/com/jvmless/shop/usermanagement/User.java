package com.jvmless.shop.usermanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class User {

    private UserId userId;
    private Set<UserRole> roles;
    private UserType userType;

    public boolean hasRole(UserRole role) {
        return roles.contains(role);
    }

    public boolean is(UserType type) {
        return type.equals(this.userType);
    }

    public boolean isAtLeast(UserType type) {
        return type.equals(this.userType) || this.userType.getPriority() >= type.getPriority();
    }
}
