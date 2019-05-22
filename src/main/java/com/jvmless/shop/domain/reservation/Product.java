package com.jvmless.shop.domain.reservation;

import com.jvmless.shop.domain.productcatalog.ProductData;
import com.jvmless.shop.domain.productcatalog.ProductId;
import com.jvmless.shop.domain.productcatalog.ProductStatus;
import com.jvmless.shop.domain.productcatalog.UserId;
import com.jvmless.shop.sharedkernel.Money;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Period;
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
    private Set<ProductReservationPolicy> productReservationPolicies;
    private Set<ProductReservationHistory> productReservationHistories;
    private UserId owner;
    private ProductStatus status;

    protected Product(ProductId productId, UserId owner) {
        this.productId = productId;
        this.owner = owner;
        this.status = ProductStatus.AVALIABLE;
    }

    public void addReservationPolicy(ProductReservationPolicy productReservationPolicy) {
        this.productReservationPolicies.add(productReservationPolicy);
    }

    /**
     * Rezerwacja pojazdu na okres czasu, nie uwzględnia kolejek w rezerwacji. To nie ta dziedzina, prodkut w danym czasie może być
     * zarezerowany do kupienia przez jednego uzytkownika, prodkut jako rezerwacja pilnuje czy user np. nie rezerwuje w kolko tego samego
     * produktu czy polityka rezerwacji tego konkretnego produktu sie zgadza. Period jest tylko value object
     * @param potentialOwner
     * @param period
     */
    public void reserve(UserId potentialOwner, Period period) {
        if(isAvailable() && canBeReserved()) {
            this.productReservationHistories.add(new ProductReservationHistory(period, potentialOwner));
            this.status = ProductStatus.RESERVED;
            this.owner = potentialOwner;
        } else {
            throw new IllegalStateException("Product cannot be reserved");
        }
    }

    public void reserve(UserId potentialOwner) {
        Period defaultPeriod = Period.ofDays(7);
        reserve(potentialOwner, defaultPeriod);
    }

    public boolean canBeReserved() {
        if(this.productReservationPolicies.isEmpty()){
            return true;
        } else {
            return this.productReservationPolicies.stream().allMatch(x -> x.canReserve(this.productReservationHistories));
        }
    }

    public boolean isAvailable() {
        return status.equals(ProductStatus.AVALIABLE);
    }

}
