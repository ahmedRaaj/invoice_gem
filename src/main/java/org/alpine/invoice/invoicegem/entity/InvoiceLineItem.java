package org.alpine.invoice.invoicegem.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@MappedSuperclass
public class InvoiceLineItem extends AlpineEntity {
    protected Long quantity;
    protected BigDecimal unitPrice;
    protected BigDecimal totalPrice;
    protected BigDecimal tax;
    protected BigDecimal discount;
    protected BigDecimal taxRate;
    protected BigDecimal discountRate;
    protected String currency;
}
