package com.jvmless.shop.sales.domain.productcatalog;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.usermanagement.UserId;

import java.util.Set;

//Policy does not need to be represent as set because strategy pattern and
//it can encapsulate rules inside current strategy implementation
public interface ProductReservationPolicy {
    boolean canReserve();
}
