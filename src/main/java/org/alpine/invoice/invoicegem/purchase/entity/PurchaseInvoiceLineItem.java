package org.alpine.invoice.invoicegem.purchase.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.alpine.invoice.invoicegem.entity.AlpineEntity;
import org.alpine.invoice.invoicegem.entity.InvoiceLineItem;
import org.alpine.invoice.invoicegem.product.entity.ProductEntity;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "purchase_invoice_line_item")
public class PurchaseInvoiceLineItem extends InvoiceLineItem {
    @ManyToOne
    @JoinColumn(name = "purchase_invoice_id")
    private PurchaseInvoice purchaseInvoice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;



}