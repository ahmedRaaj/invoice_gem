package org.alpine.invoice.invoicegem.purchase;

public class PurchaseOrderNotFound extends RuntimeException {

    public PurchaseOrderNotFound(String message) {
        super(message);
    }
}
