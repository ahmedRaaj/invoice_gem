package org.alpine.invoice.invoicegem.purchase.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class PurchaseOrderLineItemDto {
    private String categoryName;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal taxRate;
    private BigDecimal discountRate;
    private String currency;

}
