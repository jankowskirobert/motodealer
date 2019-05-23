package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.domain.productcatalog.UserId;
import com.jvmless.shop.usermanagement.User;

public interface UserRepository {
    User find(UserId userId);
}
