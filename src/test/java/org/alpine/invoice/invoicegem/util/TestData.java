package org.alpine.invoice.invoicegem.util;

import org.alpine.invoice.invoicegem.product.entity.CategoryEntity;
import org.alpine.invoice.invoicegem.product.entity.ProductEntity;

public class TestData {


    public static ProductEntity createProductWithCategory(String name, String categoryName) {
        ProductEntity product = new ProductEntity(name);
        CategoryEntity category = new CategoryEntity(categoryName);
        product.setCategoryEntity(category);
        return product;
    }
}
