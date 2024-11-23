package org.alpine.invoice.invoicegem.purchase;

import org.alpine.invoice.invoicegem.purchase.entity.PurchaseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice, Long> {
}