package org.alpine.invoice.invoicegem.purchase;

import org.alpine.invoice.invoicegem.inventory.InventoryService;
import org.alpine.invoice.invoicegem.product.entity.CategoryEntity;
import org.alpine.invoice.invoicegem.product.entity.ProductEntity;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderDto;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderLineItemDto;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseOrder;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseOrderLineItem;
import org.alpine.invoice.invoicegem.product.repository.CategoryRepository;
import org.alpine.invoice.invoicegem.product.repository.ProductRepository;
import org.alpine.invoice.invoicegem.product.ProductService;
import org.alpine.invoice.invoicegem.util.TestData;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;



@DataJpaTest
@Import({ProductService.class, InventoryService.class})
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
    @Autowired
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        purchaseOrderService = new PurchaseOrderService(purchaseOrderRepository,productService,inventoryService);
    }

    @Test
    void testProductAndCategoryGetsCreatedFromPurchaseOrder() {

        PurchaseOrderDto poDto = Instancio.create(PurchaseOrderDto.class);
        poDto.setId(null);
        PurchaseOrderLineItemDto poLineItem = Instancio.create(PurchaseOrderLineItemDto.class);
        poDto.setLineItems(List.of(poLineItem));
        poLineItem.setProductName("test");
        poLineItem.setCategoryName("testCategory");

        purchaseOrderService.createPurchaseOrder(poDto);


        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

        Assertions.assertThat(productRepository.findAll()).extracting(ProductEntity::getName).containsOnly("test");
        Assertions.assertThat(categoryRepository.findAll()).extracting(CategoryEntity::getName).containsOnly("testCategory");
        List<PurchaseOrder> allPo = purchaseOrderRepository.findAll();
        Assertions.assertThat(allPo).extracting(PurchaseOrder::getOrderNumber).containsOnly(poDto.getOrderNumber());
        Assertions.assertThat(allPo).
                satisfiesExactly( purchaseOrder ->
                        Assertions.assertThat(purchaseOrder.getPurchaseOrderLineItems())
                                .extracting(PurchaseOrderLineItem::getTotalPrice)
                .containsOnly(poLineItem.getTotalPrice()));

    }

    @Test
    void testProductAndCategoryDoesntGetCreatedFromPurchaseOrderWhenAlreadyExist() {

        PurchaseOrderDto poDto = Instancio.create(PurchaseOrderDto.class);
        poDto.setId(null);
        PurchaseOrderLineItemDto poLineItem = Instancio.create(PurchaseOrderLineItemDto.class);
        poDto.setLineItems(List.of(poLineItem));
        poLineItem.setProductName("test");
        poLineItem.setCategoryName("testCategory");
        productRepository.save(TestData.createProductWithCategory("test", "testCategory"));


        purchaseOrderService.createPurchaseOrder(poDto);

        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

        Assertions.assertThat(productRepository.findAll()).extracting(ProductEntity::getName).containsOnly("test");
        Assertions.assertThat(categoryRepository.findAll()).extracting(CategoryEntity::getName).containsOnly("testCategory");
        List<PurchaseOrder> allPo = purchaseOrderRepository.findAll();
        Assertions.assertThat(allPo).extracting(PurchaseOrder::getOrderNumber).containsOnly(poDto.getOrderNumber());
        Assertions.assertThat(allPo).
                satisfiesExactly( purchaseOrder ->
                        Assertions.assertThat(purchaseOrder.getPurchaseOrderLineItems())
                                .extracting(PurchaseOrderLineItem::getTotalPrice)
                                .containsOnly(poLineItem.getTotalPrice()));

    }


}