package com.jvmless.shop.sales.domain.productcatalog;

import com.jvmless.shop.usermanagement.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.time.Period;

/**
 * Product in context of SALE with some reservation rules in context of selling not in context i.e 'reserve for test drive'
 * ( because it is whole different context, it is involved if someone want to testdrive u cannot reserve it for someone else )
 * Pojazd do sprzedaży nie jest tym co pojazd do jazdy testowej
 */
@Accessors(fluent = true)
@Getter
@EqualsAndHashCode(of = "productId")
public class Product {

    @Id
    private ProductId productId;
    private ProductReservationPolicyType reservationPolicyType;
    private UserId owner;
    private ProductStatus status;

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

    /*
     * Can be reserved is product is:
     * -active
     * -not reserved by other user
     * -user not reserved this product in manner of policy and product history
     * -also policy gives possibility to decide if product can be sold to user
     */
    public void reserve(UserId potentialOwner, Period period, ProductReservationPolicyFactory productReservationPolicyFactory) {
        ProductReservationPolicy productReservationPolicy = productReservationPolicyFactory.generate(this.reservationPolicyType);
        if(isAvailable() && canBeReserved(potentialOwner, productReservationPolicy)) {
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
            case AVAILABLE:
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

    public void reserve(UserId potentialOwner, ProductReservationPolicyFactory productReservationPolicyFactory) {
        Period defaultPeriod = Period.ofDays(7);
        reserve(potentialOwner, defaultPeriod, productReservationPolicyFactory);
    }

    private boolean canBeReserved(UserId potentialOwner, ProductReservationPolicy productReservationPolicies) {
        if(productReservationPolicies == null){
            return !ProductStatus.RESERVED.equals(this.status);
        } else {
            return productReservationPolicies.canReserve(potentialOwner, productId);
        }
    }

    public boolean isAvailable() {
        return status.equals(ProductStatus.AVAILABLE);
    }

}
