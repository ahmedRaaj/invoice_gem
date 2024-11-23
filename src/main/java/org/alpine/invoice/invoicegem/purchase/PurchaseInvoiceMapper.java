package org.alpine.invoice.invoicegem.purchase;

import org.alpine.invoice.invoicegem.product.dto.SupplierPartDto;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderDto;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderLineItemDto;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseInvoice;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseInvoiceLineItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PurchaseInvoiceMapper {
    PurchaseInvoiceMapper INSTANCE = Mappers.getMapper(PurchaseInvoiceMapper.class);

    @Mapping(target = "purchaseInvoiceLineItems",source = "lineItems")
    PurchaseInvoice toPurchaseOrderEntity(PurchaseOrderDto purchaseOrderDto);
    @InheritInverseConfiguration
    PurchaseOrderDto toPurchaseOrderDto(PurchaseInvoice purchaseInvoice);

    List<PurchaseOrderDto> toPurchaseOrderDtoList(List<PurchaseInvoice> purchaseInvoice);

    PurchaseInvoiceLineItem toPurchaseOrderLineItemEntity(PurchaseOrderLineItemDto purchaseOrderLineItemDto);

    @Mapping(target = "name",source = "productName")
    @Mapping(target = "categoryName",source = "categoryName")
    SupplierPartDto toProductDto(PurchaseOrderLineItemDto purchaseOrderLineItemDto);

    List<SupplierPartDto> toProductDtoList(List<PurchaseOrderLineItemDto> purchaseOrderLineItemDtoList);
  }