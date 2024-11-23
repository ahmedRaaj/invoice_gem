package org.alpine.invoice.invoicegem.inventory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.alpine.invoice.invoicegem.entity.AlpineEntity;
import org.alpine.invoice.invoicegem.inventory.util.InventoryItemStatus;
import org.alpine.invoice.invoicegem.product.entity.SupplierPart;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class InventoryItemEntity extends AlpineEntity {
    @ManyToOne
    private SupplierPart supplierPart;
    private Long quantityInStock;
    private LocalDateTime lastUpdate;
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private InventoryItemStatus inventoryItemStatus;

    @OneToMany(mappedBy = "inventoryItemEntity",cascade = CascadeType.PERSIST)
    private List<InventoryItemTransactionEntity> inventoryItems;

}
