package com.jvmless.shop.usermanagement;

public interface UserRepository {
    User find(UserId userId);
    void save(User user);
}
