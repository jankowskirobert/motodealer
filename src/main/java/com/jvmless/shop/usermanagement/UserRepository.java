package com.jvmless.shop.usermanagement;

import com.jvmless.shop.sales.domain.productcatalog.UserId;

public interface UserRepository {
    User find(UserId userId);
}
