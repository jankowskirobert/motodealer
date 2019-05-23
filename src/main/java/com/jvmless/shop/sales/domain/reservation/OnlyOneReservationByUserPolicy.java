package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.usermanagement.UserId;

import java.util.Set;
/*
probably has no sense at all, because reservation history is kind of useless, need to be checked all items
 */
public class OnlyOneReservationByUserPolicy implements ProductReservationPolicy {

    ProductReservationHistoryRepository productReservationHistoryRepository;

    @Override
    public boolean canReserve(UserId potentialOwner, ProductId productId) {
        return false;
    }
}
