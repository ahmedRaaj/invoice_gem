package org.alpine.invoice.invoicegem.purchase;

import lombok.AllArgsConstructor;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderDto;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseOrder;
import org.alpine.invoice.invoicegem.service.ProductService;
import org.springframework.stereotype.Service;

import static org.alpine.invoice.invoicegem.purchase.PurchaseOrderMapper.INSTANCE;

@Service
@AllArgsConstructor
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductService productService;


    public void createPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
        productService.insertProductRecordIfMissing(INSTANCE.toProductDtoList(purchaseOrderDto.getLineItems()));
        PurchaseOrder poEntity = INSTANCE.toPurchaseOrderEntity(purchaseOrderDto);
        purchaseOrderRepository.save(poEntity);
    }
}
