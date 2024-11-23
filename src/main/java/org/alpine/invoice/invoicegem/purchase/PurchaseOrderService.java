package org.alpine.invoice.invoicegem.purchase;

import lombok.AllArgsConstructor;
import org.alpine.invoice.invoicegem.constant.PurchaseOrderStatus;
import org.alpine.invoice.invoicegem.inventory.InventoryService;
import org.alpine.invoice.invoicegem.product.ProductService;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderDto;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseOrder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.alpine.invoice.invoicegem.purchase.PurchaseOrderMapper.INSTANCE;

@Service
@AllArgsConstructor
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductService productService;
    private final InventoryService inventoryService;


    public Long createPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
        productService.insertProductRecordIfMissing(INSTANCE.toProductDtoList(purchaseOrderDto.getLineItems()));
        PurchaseOrder poEntity = INSTANCE.toPurchaseOrderEntity(purchaseOrderDto);
        poEntity.setStatus(PurchaseOrderStatus.CREATED);
        purchaseOrderRepository.save(poEntity);
        return poEntity.getId();
    }

    public void shelvePurchasedItems(Long purchaseOrderId) {
        purchaseOrderRepository.findById(purchaseOrderId).ifPresent(poEntity -> {
            poEntity.setStatus(PurchaseOrderStatus.GOODS_SHELVED);
            purchaseOrderRepository.save(poEntity);
            inventoryService.shelve(poEntity);
        });

    }


    public PurchaseOrderDto getPurchaseOrderById(Long id) {
        PurchaseOrder poEntity = purchaseOrderRepository.findById(id).orElseThrow(() -> new PurchaseOrderNotFound("Purchase Order not found"));
        return INSTANCE.toPurchaseOrderDto(poEntity);
    }

    public List<PurchaseOrderDto> getAllPurchaseOrders() {
        return INSTANCE.toPurchaseOrderDtoList(purchaseOrderRepository.findAll());
    }


}
