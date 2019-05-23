package com.jvmless.shop.sales.domain.reservation;

import java.util.Set;

public interface ProductReservationPolicy {
    boolean canReserve(Set<ProductReservationHistory> productReservationHistories);
}
