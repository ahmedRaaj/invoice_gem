package org.alpine.invoice.invoicegem.util;

import org.alpine.invoice.invoicegem.product.entity.CategoryEntity;
import org.alpine.invoice.invoicegem.product.entity.ProductEntity;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderDto;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderLineItemDto;
import org.instancio.Instancio;

import java.util.List;

public class TestData {

    public static final String PRODUCT_NAME = "test";
    public static final String CATEGORY_NAME = "testCategory";
    public static ProductEntity createProductWithCategory(String name, String categoryName) {
        ProductEntity product = new ProductEntity(name);
        CategoryEntity category = new CategoryEntity(categoryName);
        product.setCategoryEntity(category);
        return product;
    }


    public static PurchaseOrderDto createPurchseOrderDto(String productName, String categoryName) {
        PurchaseOrderDto poDto = Instancio.create(PurchaseOrderDto.class);
        poDto.setId(null);
        PurchaseOrderLineItemDto poLineItem = Instancio.create(PurchaseOrderLineItemDto.class);
        poDto.setLineItems(List.of(poLineItem));
        poLineItem.setProductName(productName);
        poLineItem.setCategoryName(categoryName);
        return poDto;
    }
}
