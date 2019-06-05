package com.jvmless.shop.sales.domain.productcatalog;

import com.jvmless.shop.core.DomainException;
import com.jvmless.shop.usermanagement.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.time.Period;

@Accessors(fluent = true)
@Getter
@EqualsAndHashCode(of = "productId")
public class Product {

    @Id
    private ProductId productId;
    private ProductReservationPolicyType reservationPolicyType;
    private UserId owner;
    private ProductStatus status;
    private MotorcycleTechnicalDetails motorcycleTechnicalDetails;

    public Product(ProductId productId, UserId owner) {
        this.productId = productId;
        this.owner = owner;
        this.status = ProductStatus.AVAILABLE;
        this.reservationPolicyType = ProductReservationPolicyType.ALL;
    }

    public Product(ProductId productId, UserId owner, ProductReservationPolicyType productReservationPolicyType) {
        this.productId = productId;
        this.owner = owner;
        this.status = ProductStatus.AVAILABLE;
        this.reservationPolicyType = productReservationPolicyType;
    }

    public void reserve(UserId potentialOwner, Period period, ProductReservationPolicyFactory productReservationPolicyFactory) {
        ProductReservationPolicy productReservationPolicy = productReservationPolicyFactory.generate(this.reservationPolicyType);
        if (isAvailable() && canBeReserved(potentialOwner, productReservationPolicy)) {
            this.status = ProductStatus.RESERVED;
            this.owner = potentialOwner;
        } else {
            throw new DomainException("Product cannot be reserved");
        }
    }

    public void cancelReservation() {
        if (isReserved()) {
            this.owner = null;
            this.status = ProductStatus.AVAILABLE;
        } else if (isAvailable()) {
            throw new DomainException("Product is currently not reserved");
        } else if (isSold()) {
            throw new DomainException("Cannot cancel reservation, product is sold");
        }
    }

    private boolean isSold() {
        return ProductStatus.SOLD.equals(this.status);
    }

    public void close(UserId userId) {
        //only if sold
    }

    public void sell(UserId newOwner) {
        if (isReserved() && newOwner.equals(this.owner)) {
            //one more validation

            this.status = ProductStatus.SOLD;
        } else if (isAvailable()) {
            this.owner = newOwner;
            this.status = ProductStatus.SOLD;
        } else if (isSold()) {
            throw new DomainException("Cannot cancel sell, product is already sold");
        }
    }

    public void reserve(UserId potentialOwner, ProductReservationPolicyFactory productReservationPolicyFactory) {
        Period defaultPeriod = Period.ofDays(7);
        reserve(potentialOwner, defaultPeriod, productReservationPolicyFactory);
    }

    private boolean canBeReserved(UserId potentialOwner, ProductReservationPolicy productReservationPolicies) {
        if (productReservationPolicies == null) {
            return !ProductStatus.RESERVED.equals(this.status);
        } else {
            return productReservationPolicies.canReserve(potentialOwner, productId);
        }
    }

    public boolean isAvailable() {
        return ProductStatus.AVAILABLE.equals(this.status);
    }

    public boolean isReserved() {
        return ProductStatus.RESERVED.equals(this.status);
    }

    public void updateDetails(MotorcycleTechnicalDetails motorcycleTechnicalDetails) {
        if (isAvailable()) {
            this.motorcycleTechnicalDetails = motorcycleTechnicalDetails;
        }
    }
}
