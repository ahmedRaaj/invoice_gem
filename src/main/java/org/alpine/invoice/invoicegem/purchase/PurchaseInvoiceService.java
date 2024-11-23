package org.alpine.invoice.invoicegem.purchase;

import lombok.AllArgsConstructor;
import org.alpine.invoice.invoicegem.inventory.InventoryService;
import org.alpine.invoice.invoicegem.product.ProductService;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderDto;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseInvoice;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.alpine.invoice.invoicegem.purchase.PurchaseInvoiceMapper.INSTANCE;

@Service
@AllArgsConstructor
public class PurchaseInvoiceService {
    private final PurchaseInvoiceRepository purchaseInvoiceRepository;
    private final ProductService productService;
    private final InventoryService inventoryService;


    public Long createPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
        productService.insertProductRecordIfMissing(INSTANCE.toProductDtoList(purchaseOrderDto.getLineItems()));
        PurchaseInvoice poEntity = INSTANCE.toPurchaseOrderEntity(purchaseOrderDto);
        poEntity.setStatus(PurchaseInvoiceStatus.CREATED);
        purchaseInvoiceRepository.save(poEntity);
        return poEntity.getId();
    }

    public void shelvePurchasedItems(Long purchaseOrderId) {
        purchaseInvoiceRepository.findById(purchaseOrderId).ifPresent(poEntity -> {
            poEntity.setStatus(PurchaseInvoiceStatus.GOODS_SHELVED);
            purchaseInvoiceRepository.save(poEntity);
            inventoryService.shelve(poEntity);
        });

    }


    public PurchaseOrderDto getPurchaseOrderById(Long id) {
        PurchaseInvoice poEntity = purchaseInvoiceRepository.findById(id).orElseThrow(() -> new PurchaseInvoiceNotFound("Purchase Order not found"));
        return INSTANCE.toPurchaseOrderDto(poEntity);
    }

    public List<PurchaseOrderDto> getAllPurchaseOrders() {
        return INSTANCE.toPurchaseOrderDtoList(purchaseInvoiceRepository.findAll());
    }


}
