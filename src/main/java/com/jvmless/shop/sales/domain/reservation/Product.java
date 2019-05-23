package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.sales.domain.productcatalog.ProductStatus;
import com.jvmless.shop.usermanagement.UserId;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Period;
import java.util.HashSet;
import java.util.Set;

/**
 * Product in context of SALE with some reservation rules in context of selling not in context i.e 'reserve for test drive'
 * ( because it is whole different context, it is involved if someone want to testdrive u cannot reserve it for someone else )
 * Pojazd do sprzedaży nie jest tym co pojazd do jazdy testowej
 */
@Accessors(fluent = true)
@Getter
public class Product {

    private ProductId productId;
    private Set<ProductReservationHistory> productReservationHistories = new HashSet<>();
    private UserId owner;
    private ProductStatus status;

    protected Product(ProductId productId, UserId owner) {
        this.productId = productId;
        this.owner = owner;
        this.status = ProductStatus.AVALIABLE;
    }

    public void reserve(UserId potentialOwner, Period period, ProductReservationPolicy productReservationPolicies) {
        if(isAvailable() && canBeReserved(potentialOwner, productReservationPolicies)) {
            this.productReservationHistories.add(new ProductReservationHistory(period, potentialOwner));
            this.status = ProductStatus.RESERVED;
            this.owner = potentialOwner;
        } else {
            throw new IllegalStateException("Product cannot be reserved");
        }
    }

    public void close(UserId userId) {
        //only if sold
    }

    public void sell(UserId newOwner) {
        switch(status) {
            case AVALIABLE:
                this.owner = newOwner;
                this.status = ProductStatus.SOLD;
                break;
            case RESERVED:
                if(!this.owner.equals(newOwner))
                    throw new IllegalArgumentException("Cannot sell product, it is currently reserved by other user");
                else
                    this.status = ProductStatus.SOLD;
                break;
            default:
                throw new IllegalStateException("Product cannot be sold");
        }
    }

    public void reserve(UserId potentialOwner, ProductReservationPolicy productReservationPolicies) {
        Period defaultPeriod = Period.ofDays(7);
        reserve(potentialOwner, defaultPeriod, productReservationPolicies);
    }

    public boolean canBeReserved(UserId potentialOwner, ProductReservationPolicy productReservationPolicies) {
        if(productReservationPolicies == null){
            return !ProductStatus.RESERVED.equals(this.status);
        } else {
            return productReservationPolicies.canReserve(potentialOwner, this.productReservationHistories);
        }
    }

    public boolean isAvailable() {
        return status.equals(ProductStatus.AVALIABLE);
    }

}
