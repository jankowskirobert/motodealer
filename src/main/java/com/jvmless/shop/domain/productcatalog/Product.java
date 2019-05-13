package com.jvmless.shop.domain.productcatalog;

import com.jvmless.shop.sharedkernel.Money;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public class Product {

    private ProductId productId;
    private MotorcycleTechnicalDetails motorcycleTechnicalDetails;
    private UserId owner;
    private Money price;
    private ProductStatus status;

    protected Product(ProductId productId, MotorcycleTechnicalDetails motorcycleTechnicalDetails, UserId owner, Money price) {
        this.productId = productId;
        this.motorcycleTechnicalDetails = motorcycleTechnicalDetails;
        this.owner = owner;
        this.price = price;
        this.status = ProductStatus.AVALIABLE;
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

    public void reserve(UserId potentialOwner) {
        if(isAvailable()) {
            this.status = ProductStatus.RESERVED;
            this.owner = potentialOwner;
        } else {
            throw new IllegalStateException("Product cannot be reserved");
        }
    }

    public void updateTechnicalData(MotorcycleTechnicalDetails newMotorcycleTechnicalDetails) {
        if(isAvailable() && newMotorcycleTechnicalDetails.equals(this.motorcycleTechnicalDetails)) {
            this.motorcycleTechnicalDetails = newMotorcycleTechnicalDetails;
        } else {
            throw new IllegalStateException("Motorcycle data cannot be updated");

        }
    }

    public void changePrice(Money newPrice) {
        if(isAvailable()) {
            this.price = newPrice;
        } else {
            throw new IllegalStateException("Cannot change price, change is no longer available");
        }
    }

    public boolean isAvailable() {
        return status.equals(ProductStatus.AVALIABLE);
    }

}
