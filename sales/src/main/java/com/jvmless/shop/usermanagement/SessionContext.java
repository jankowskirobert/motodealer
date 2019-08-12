package com.jvmless.shop.usermanagement;

import org.springframework.stereotype.Component;

@Component
public class SessionContext implements UserContextService {
    @Override
    public UserId getCurrentUserId() {
        return null;
    }
}
