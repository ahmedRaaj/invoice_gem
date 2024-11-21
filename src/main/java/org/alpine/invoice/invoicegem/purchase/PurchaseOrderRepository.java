package org.alpine.invoice.invoicegem.purchase;

import org.alpine.invoice.invoicegem.purchase.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}