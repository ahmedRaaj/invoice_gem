package org.alpine.invoice.invoicegem.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.alpine.invoice.invoicegem.inventory.util.InventoryItemStatus;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO for {@link InventoryItemEntity}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public final class InventoryItemEntityDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private final Long id;
    private final Long quantityInStock;
    private final Long quantityOutStock;
    private final Long adjustedQuantityInStock;
    private final Long adjustedQuantityOutStock;
    private final LocalDateTime lastUpdate;
    private final LocalDateTime creationDate;
    private final BigDecimal purchasePrice;
    private final Boolean isManuallyAdjusted;
    private final InventoryItemStatus inventoryItemStatus;


}