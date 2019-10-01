package com.jvmless.shop.sales.domain.product;

import com.jvmless.shop.core.DomainException;
import com.jvmless.shop.usermanagement.UserId;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Accessors(fluent = true)
@Getter
@EqualsAndHashCode(of = "productId")
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Product implements Serializable {

    @EmbeddedId
    private ProductId productId;
    private ProductReservationPolicyType reservationPolicyType;
    private UserId owner = null;
    private ProductStatus status;

    public Product(ProductId productId) {
        this.productId = productId;
        this.status = ProductStatus.AVAILABLE;
        this.reservationPolicyType = ProductReservationPolicyType.ALL;
    }

    public Product(ProductId productId, ProductReservationPolicyType productReservationPolicyType) {
        this.productId = productId;
        this.status = ProductStatus.AVAILABLE;
        this.reservationPolicyType = productReservationPolicyType;
    }

    public void cancelReservation() {
        if (isReserved()) {
            this.owner = null;
            this.status = ProductStatus.AVAILABLE;
        } else if (isAvailable()) {
            throw new DomainException(String.format("Product %s is currently not reserved", productId.getId()));
        } else if (isSold()) {
            throw new DomainException("Cannot cancel reservation, product is sold");
        }
    }

    private boolean isSold() {
        return ProductStatus.SOLD.equals(this.status);
    }

    public void updateReservationPoicy(@NonNull ProductReservationPolicyType productReservationPolicyType) {
        if (isAvailable()) {
            this.reservationPolicyType = productReservationPolicyType;
        } else {
            throw new IllegalStateException("Product data is unable to update in current state");
        }
    }

    public void sell(@NonNull UserId newOwner) {
        if (isReserved() && newOwner.equals(this.owner)) {

            this.status = ProductStatus.SOLD;

        } else if (isAvailable()) {
            this.owner = newOwner;
            this.status = ProductStatus.SOLD;
        } else {
            throw new DomainException("Cannot cancel sell, product is unavailable");
        }
    }

    public void reserve(@NonNull UserId potentialOwner, @NonNull ProductReservationPolicy productReservationPolicy) {
        if (isAvailable() && canBeReserved(productReservationPolicy)) {
            this.status = ProductStatus.RESERVED;
            this.owner = potentialOwner;
        } else {
            throw new DomainException("Product cannot be reserved because is already reserved");
        }
    }

    private boolean canBeReserved(ProductReservationPolicy productReservationPolicies) {
        if (productReservationPolicies == null) {
            return !ProductStatus.RESERVED.equals(this.status);
        } else {
            return productReservationPolicies.canReserve();
        }
    }

    public boolean isAvailable() {
        return ProductStatus.AVAILABLE.equals(this.status);
    }

    public boolean isReserved() {
        return ProductStatus.RESERVED.equals(this.status);
    }

}
