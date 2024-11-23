package org.alpine.invoice.invoicegem.inventory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.alpine.invoice.invoicegem.entity.AlpineEntity;
import org.alpine.invoice.invoicegem.inventory.util.InventoryTransactionStatus;
import org.alpine.invoice.invoicegem.inventory.util.InventoryTransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "inventory_item_transaction")
public class InventoryItemTransactionEntity extends AlpineEntity {
    @ManyToOne
    @JoinColumn(name = "inventory_item_id")
    private InventoryItemEntity inventoryItemEntity;
    @Enumerated(EnumType.STRING)
    private InventoryTransactionType transactionType;
    private Long quantity;

    //todo to remove below attributes later.
    private BigDecimal unitPrice;
    private BigDecimal taxRate = BigDecimal.ZERO;
    private BigDecimal discountRate = BigDecimal.ZERO;
    //remove all above

    private LocalDate transactionDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private InventoryTransactionStatus transactionStatus;



    public BigDecimal getTotalPrice(){
        BigDecimal amount = unitPrice.multiply(new BigDecimal(quantity));
        BigDecimal taxAmount = getTaxAmount(amount);
        BigDecimal discountAmount = getDiscountAmount(amount);

        return amount.add(taxAmount).add(discountAmount.negate());
    }

    private BigDecimal getDiscountAmount(BigDecimal amount) {
        return amount.add(discountRate.multiply(amount));

    }

    private BigDecimal getTaxAmount(BigDecimal amount) {
        return amount.add(taxRate.multiply(amount));
    }


}