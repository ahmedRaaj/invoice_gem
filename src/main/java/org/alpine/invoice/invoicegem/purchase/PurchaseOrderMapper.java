package org.alpine.invoice.invoicegem.purchase;

import org.alpine.invoice.invoicegem.product.dto.ProductDto;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderDto;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderLineItemDto;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseOrder;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseOrderLineItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PurchaseOrderMapper {

    PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);
    @Mapping(target = "purchaseOrderLineItems",source = "lineItems")
    PurchaseOrder toPurchaseOrderEntity(PurchaseOrderDto purchaseOrderDto);
    PurchaseOrderLineItem toPurchaseOrderLineItemEntity(PurchaseOrderLineItemDto purchaseOrderLineItemDto);


    @Mapping(target = "name",source = "productName")
    @Mapping(target = "categoryName",source = "categoryName")
    ProductDto toProductDto(PurchaseOrderLineItemDto purchaseOrderLineItemDto);


    List<ProductDto> toProductDtoList(List<PurchaseOrderLineItemDto> purchaseOrderLineItemDtoList);

  }