package com.jvmless.shop.usermanagement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Embeddable
public class UserId implements Serializable {

    private String userId;

    public static UserId of(String id) {
        return new UserId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(this.userId, userId.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
