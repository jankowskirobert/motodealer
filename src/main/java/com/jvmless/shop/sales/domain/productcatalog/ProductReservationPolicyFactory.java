package com.jvmless.shop.sales.domain.productcatalog;

import com.jvmless.shop.usermanagement.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductReservationPolicyFactory {

    private UserRepository userRepository;

    public ProductReservationPolicy generate(ProductReservationPolicyType reservationPolicyType) {
        switch (reservationPolicyType) {
            case ALL:
                return (potentialOwner, productId) -> true;
            case ONLY_PREMIUM:
                return new OnlyPremium(userRepository);
            default:
                return (potentialOwner, productId) -> false;
        }
    }
}
