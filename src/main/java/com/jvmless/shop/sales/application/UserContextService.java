package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.domain.productcatalog.UserId;

public interface UserContextService {
    UserId getCurrentUserId();
}
