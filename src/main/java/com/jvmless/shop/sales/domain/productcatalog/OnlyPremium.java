package com.jvmless.shop.sales.domain.productcatalog;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.sales.domain.productcatalog.ProductReservationPolicy;
import com.jvmless.shop.usermanagement.UserId;
import com.jvmless.shop.usermanagement.UserRepository;
import com.jvmless.shop.usermanagement.UserRole;
import com.jvmless.shop.usermanagement.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OnlyPremium implements ProductReservationPolicy {

    private UserRepository userRepository;

    @Override
    public boolean canReserve(UserId potentialOwner, ProductId productId) {
        return userRepository.find(potentialOwner).is(UserType.PREMIUM);
    }
}
