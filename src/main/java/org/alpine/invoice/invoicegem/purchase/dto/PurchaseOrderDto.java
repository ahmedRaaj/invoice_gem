package org.alpine.invoice.invoicegem.purchase.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Data
public class PurchaseOrderDto {

    private Long id;
    private String orderNumber;
    private LocalDate orderDate;
    private BigDecimal totalOrderAmountBeforeTax;
    private LocalDate deliveryDate;
    private BigDecimal taxAmount = BigDecimal.ZERO;
    private BigDecimal taxRate = BigDecimal.ZERO;
    private BigDecimal totalTaxAmountAfterTax;
    private List<PurchaseOrderLineItemDto> lineItems;

}
