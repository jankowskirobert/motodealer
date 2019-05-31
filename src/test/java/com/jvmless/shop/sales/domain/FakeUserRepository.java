package com.jvmless.shop.sales.domain;

import com.jvmless.shop.usermanagement.User;
import com.jvmless.shop.usermanagement.UserId;
import com.jvmless.shop.usermanagement.UserRepository;

import java.util.concurrent.ConcurrentHashMap;

public class FakeUserRepository implements UserRepository {

    private ConcurrentHashMap<UserId, User> inMemory = new ConcurrentHashMap<>();

    @Override
    public User find(UserId userId) {
        return inMemory.get(userId);
    }

    @Override
    public void save(User user) {
        inMemory.put(user.getUserId(), user);
    }
}
