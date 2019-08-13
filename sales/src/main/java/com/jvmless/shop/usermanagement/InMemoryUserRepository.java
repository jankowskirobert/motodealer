package com.jvmless.shop.usermanagement;

import com.jvmless.shop.usermanagement.User;
import com.jvmless.shop.usermanagement.UserId;
import com.jvmless.shop.usermanagement.UserRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryUserRepository implements UserRepository {

    private ConcurrentHashMap<UserId, User> inMemory = new ConcurrentHashMap<>();

    @Override
    public User find(UserId userId) {
        return inMemory.get(userId);
    }


    public void clear() {
        inMemory.clear();
    }

    public void save(User user) {
        inMemory.put(user.getUserId(), user);
    }
}
