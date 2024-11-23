package org.alpine.invoice.invoicegem.inventory;

import lombok.AllArgsConstructor;
import org.alpine.invoice.invoicegem.inventory.entity.InventoryItemEntity;
import org.alpine.invoice.invoicegem.inventory.entity.InventoryItemTransactionEntity;
import org.alpine.invoice.invoicegem.inventory.repository.InventoryItemEntityRepository;
import org.alpine.invoice.invoicegem.inventory.util.InventoryItemStatus;
import org.alpine.invoice.invoicegem.inventory.util.InventoryTransactionStatus;
import org.alpine.invoice.invoicegem.inventory.util.InventoryTransactionType;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseInvoice;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseInvoiceLineItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryItemEntityRepository inventoryItemRepository;

    private final Function<PurchaseInvoiceLineItem,InventoryItemEntity> poLineItemToInventoryItemMapper = (poLineItem)->{
        InventoryItemEntity invItem = new InventoryItemEntity();
        invItem.setSupplierPart(poLineItem.getSupplierPart());
        invItem.setInventoryItemStatus(InventoryItemStatus.PLACED);

        InventoryItemTransactionEntity transaction = new InventoryItemTransactionEntity();
        transaction.setInventoryItemEntity(invItem);
        transaction.setTransactionType(InventoryTransactionType.INCOMING);
        transaction.setQuantity(poLineItem.getQuantity());
        transaction.setUnitPrice(poLineItem.getUnitPrice());
        transaction.setTaxRate(poLineItem.getTaxRate());
        transaction.setTransactionStatus(InventoryTransactionStatus.APPROVED);
        invItem.setInventoryItems(List.of(transaction));


        return invItem;
    };

    public void shelve(PurchaseInvoice newOrder) {
        List<InventoryItemEntity> inventoryItemEntities = creteInventoryFromIncomingOrder(newOrder);
        inventoryItemRepository.saveAll(inventoryItemEntities);
    }

    private List<InventoryItemEntity> creteInventoryFromIncomingOrder(PurchaseInvoice newOrder) {
       return newOrder.getPurchaseInvoiceLineItems().stream().map(poLineItemToInventoryItemMapper)
                .collect(Collectors.toList());
    }
}
