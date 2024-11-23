package org.alpine.invoice.invoicegem.purchase.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.alpine.invoice.invoicegem.entity.AlpineEntity;
import org.alpine.invoice.invoicegem.product.entity.ProductEntity;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "purchase_order_line_item")
public class PurchaseOrderLineItem extends AlpineEntity {
    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    private Long quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal taxRate;
    private BigDecimal discountRate;
    private String currency;

}