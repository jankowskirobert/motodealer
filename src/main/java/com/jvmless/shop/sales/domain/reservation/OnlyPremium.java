package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.usermanagement.UserId;
import com.jvmless.shop.usermanagement.UserRepository;
import com.jvmless.shop.usermanagement.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OnlyPremium implements ProductReservationPolicy {

    private UserRepository userRepository;

    @Override
    public boolean canReserve(UserId potentialOwner, ProductId productId) {
        return userRepository.find(potentialOwner).hasRole(UserRole.PREMIUM);
    }
}