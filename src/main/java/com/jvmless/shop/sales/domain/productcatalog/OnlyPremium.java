package com.jvmless.shop.sales.domain.productcatalog;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.sales.domain.productcatalog.ProductReservationPolicy;
import com.jvmless.shop.usermanagement.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OnlyPremium implements ProductReservationPolicy {

    private UserRepository userRepository;

    @Override
    public boolean canReserve(UserId potentialOwner, ProductId productId) {
        User user = userRepository.find(potentialOwner);
        if (user == null)
            return false;
        boolean result = user.is(UserType.PREMIUM);
        return result;
    }
}
