package com.jvmless.shop.usermanagement;


public class UserServiceIntegrationApiImpl implements UserServiceIntegrationApi {
    @Override
    public User find(UserId id) {
        throw new IllegalArgumentException("User not found");
    }
}
