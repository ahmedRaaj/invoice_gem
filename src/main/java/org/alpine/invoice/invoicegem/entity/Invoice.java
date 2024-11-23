package org.alpine.invoice.invoicegem.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public class Invoice extends AlpineEntity implements Serializable {
    protected String invoiceNumber;
    protected LocalDate orderDate;
    protected BigDecimal totalOrderAmountBeforeTax;
    protected LocalDate deliveryDate;
    protected BigDecimal taxAmount = BigDecimal.ZERO;
    protected BigDecimal taxRate = BigDecimal.ZERO;
    protected BigDecimal totalTaxAmountAfterTax;
}
