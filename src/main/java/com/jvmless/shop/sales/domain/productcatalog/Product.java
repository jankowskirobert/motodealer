package com.jvmless.shop.sales.domain.productcatalog;

import com.jvmless.shop.core.DomainException;
import com.jvmless.shop.usermanagement.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.Period;

@Accessors(fluent = true)
@Getter
@EqualsAndHashCode(of = "productId")
@Entity
public class Product implements Serializable {

    @EmbeddedId
    private ProductId productId;
    private ProductReservationPolicyType reservationPolicyType;
    private UserId owner = null;
    private ProductStatus status;
    @MapsId("catalogNumber")
    @OneToOne
    private MotorcycleTechnicalDetails motorcycleTechnicalDetails;

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

    public void reserve(UserId potentialOwner, Period period, ProductReservationPolicy productReservationPolicy) {
        if (isAvailable() && canBeReserved(productReservationPolicy)) {
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

            this.status = ProductStatus.SOLD;

        } else if (isAvailable()) {
            this.owner = newOwner;
            this.status = ProductStatus.SOLD;
        } else {
            throw new DomainException("Cannot cancel sell, product is unavailable");
        }
    }

    public void reserve(UserId potentialOwner, ProductReservationPolicy productReservationPolicy) {
        Period defaultPeriod = Period.ofDays(7);
        reserve(potentialOwner, defaultPeriod, productReservationPolicy);
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

    public void updateDetails(MotorcycleTechnicalDetails motorcycleTechnicalDetails) {
        if (isAvailable()) {
            this.motorcycleTechnicalDetails = motorcycleTechnicalDetails;
        }
    }
}
