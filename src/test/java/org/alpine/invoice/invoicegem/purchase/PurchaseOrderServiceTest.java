package org.alpine.invoice.invoicegem.purchase;

import org.alpine.invoice.invoicegem.product.entity.Category;
import org.alpine.invoice.invoicegem.product.entity.Product;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderDto;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderLineItemDto;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseOrder;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseOrderLineItem;
import org.alpine.invoice.invoicegem.repository.CategoryRepository;
import org.alpine.invoice.invoicegem.repository.ProductRepository;
import org.alpine.invoice.invoicegem.service.ProductService;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Import(ProductService.class)
class PurchaseOrderServiceTest {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
        purchaseOrderService = new PurchaseOrderService(purchaseOrderRepository,productService);
    }

    @Test
    void testProductGetCreated() {

        PurchaseOrderDto poDto = Instancio.create(PurchaseOrderDto.class);
        PurchaseOrderLineItemDto poLineItem = Instancio.create(PurchaseOrderLineItemDto.class);
        poDto.setLineItems(List.of(poLineItem));
        poLineItem.setProductName("test");
        poLineItem.setCategoryName("testCategory");

        purchaseOrderService.createPurchaseOrder(poDto);

        Assertions.assertThat(productRepository.findAll()).extracting(Product::getName).containsOnly("test");
        Assertions.assertThat(categoryRepository.findAll()).extracting(Category::getName).containsOnly("testCategory");
        List<PurchaseOrder> allPo = purchaseOrderRepository.findAll();
        Assertions.assertThat(allPo).extracting(PurchaseOrder::getOrderNumber).containsOnly(poDto.getOrderNumber());
        Assertions.assertThat(allPo).
                satisfiesExactly( purchaseOrder ->
                        Assertions.assertThat(purchaseOrder.getPurchaseOrderLineItems())
                                .extracting(PurchaseOrderLineItem::getTotalPrice)
                .containsOnly(poLineItem.getTotalPrice()));



    }

}