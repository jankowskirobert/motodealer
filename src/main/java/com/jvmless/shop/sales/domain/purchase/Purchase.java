package com.jvmless.shop.sales.domain.purchase;

import com.jvmless.shop.core.CorrelationId;
import com.jvmless.shop.sharedkernel.Money;
import com.jvmless.shop.usermanagement.UserId;

import java.time.LocalDateTime;
import java.util.List;

public class Purchase {
    private PurchaseId purchaseId;
    private CorrelationId correlationId;
    private List<PurchaseItem> items;
    private Money totalCost;
    private UserId userId;
    private LocalDateTime purchaseDate;

    private PurchaseStatus status;

    public Purchase(PurchaseId purchaseId, CorrelationId correlationId, UserId userId) {
        this.purchaseId = purchaseId;
        this.correlationId = correlationId;
        this.userId = userId;
        this.purchaseDate = LocalDateTime.now();
        this.status = PurchaseStatus.IN_PROGRESS;
    }
}
