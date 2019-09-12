package com.jvmless.shop.sales.domain.product;

import com.jvmless.shop.usermanagement.User;
import com.jvmless.shop.usermanagement.UserId;
import com.jvmless.shop.usermanagement.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductReservationPolicyFactory {

    private UserRepository userRepository;

    // uciecie zaleznosci, w takim wypadku sposob ladowania polityk moze byc uniezaleniony od sposobu pozyskania Usera
    // moze to byc repo moze to byc api
    public ProductReservationPolicy generate(ProductReservationPolicyType reservationPolicyType, UserId potentialOwner) {
        User user = userRepository.find(potentialOwner);
        if (user == null)
            throw new IllegalArgumentException("User not found!");
        switch (reservationPolicyType) {
            case ALL:
                return () -> true;
            case ONLY_PREMIUM:
                return new OnlyPremium(user);
            default:
                return () -> false;
        }
    }
}
