package com.jvmless.shop.sales.application;

import com.jvmless.shop.usermanagement.UserId;

public interface UserContextService {
    UserId getCurrentUserId();
}
