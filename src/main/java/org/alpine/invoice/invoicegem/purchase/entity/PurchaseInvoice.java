package org.alpine.invoice.invoicegem.purchase.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.alpine.invoice.invoicegem.entity.Invoice;
import org.alpine.invoice.invoicegem.purchase.PurchaseInvoiceStatus;
import org.alpine.invoice.invoicegem.entity.AlpineEntity;
import org.alpine.invoice.invoicegem.entity.Supplier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class PurchaseInvoice extends Invoice {



    @ManyToOne(cascade = CascadeType.PERSIST)
    private Supplier supplier;
    @OneToMany( mappedBy = "purchaseInvoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseInvoiceLineItem> purchaseInvoiceLineItems;

    @Enumerated(EnumType.STRING)
    private PurchaseInvoiceStatus status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "purchaseInvoice")
    private List<BillPayment> payments;
}
