package org.alpine.invoice.invoicegem.purchase.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.alpine.invoice.invoicegem.constant.PurchaseOrderStatus;
import org.alpine.invoice.invoicegem.entity.AlpineEntity;
import org.alpine.invoice.invoicegem.entity.Supplier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class PurchaseOrder extends AlpineEntity {

    private String orderNumber;
    private LocalDate orderDate;
    private BigDecimal totalOrderAmountBeforeTax;
    private LocalDate deliveryDate;
    private BigDecimal taxAmount = BigDecimal.ZERO;
    private BigDecimal taxRate = BigDecimal.ZERO;
    private BigDecimal totalTaxAmountAfterTax;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Supplier supplier;
    @OneToMany( mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderLineItem> purchaseOrderLineItems;

    @Enumerated(EnumType.STRING)
    private PurchaseOrderStatus status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "purchaseOrder")
    private List<BillPayment> payments;
}
