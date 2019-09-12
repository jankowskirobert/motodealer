package com.jvmless.shop.sales.domain.product;

import com.jvmless.shop.usermanagement.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OnlyPremium implements ProductReservationPolicy {

    private User potentialOwner;

    @Override
    public boolean canReserve() {
        boolean result = potentialOwner.is(UserType.PREMIUM);
        return result;
    }
}
