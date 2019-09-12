package com.jvmless.shop.sales.domain.product;

//Policy does not need to be represent as set because strategy pattern and
//it can encapsulate rules inside current strategy implementation
public interface ProductReservationPolicy {
    boolean canReserve();
}
