package org.alpine.invoice.invoicegem.inventory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.alpine.invoice.invoicegem.entity.AlpineEntity;
import org.alpine.invoice.invoicegem.inventory.util.InventoryItemStatus;
import org.alpine.invoice.invoicegem.product.entity.ProductEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class InventoryItemEntity extends AlpineEntity {
    @ManyToOne
    private ProductEntity productEntity;
    private Long quantityInStock;
    private Long quantityOutStock;
    private Long adjustedQuantityInStock;
    private Long adjustedQuantityOutStock;
    private LocalDateTime lastUpdate;
    private LocalDateTime creationDate;
    private BigDecimal purchasePrice;
    private Boolean isManuallyAdjusted = false;

    @Enumerated(EnumType.STRING)
    private InventoryItemStatus inventoryItemStatus;

    @OneToMany(mappedBy = "inventoryItemEntity",cascade = CascadeType.PERSIST)
    private List<InventoryItemTransactionEntity> inventoryItems;

}
