package org.alpine.invoice.invoicegem.purchase.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.alpine.invoice.invoicegem.entity.AlpineEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class BillPayment extends AlpineEntity {
    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseInvoice purchaseInvoice;

    private LocalDate paymentDate;
    private BigDecimal amount;
    private String currency;
    private String description;

}