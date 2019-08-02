package com.jvmless.shop.usermanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Set;

@Getter
@AllArgsConstructor
@Entity
public class User {
    @EmbeddedId
    private UserId userId;
    private Set<UserRole> roles;
    private UserType userType;

    public boolean hasRole(UserRole role) {
        return roles.contains(role);
    }

    public boolean is(UserType type) {
        return type.equals(this.userType);
    }

    public boolean isTypeOrHigher(UserType type) {
        return type.equals(this.userType) || this.userType.getPriority() >= type.getPriority();
    }
}
