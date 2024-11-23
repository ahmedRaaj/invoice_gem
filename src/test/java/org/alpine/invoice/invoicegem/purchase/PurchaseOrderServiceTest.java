package org.alpine.invoice.invoicegem.purchase;

import org.alpine.invoice.invoicegem.inventory.InventoryService;
import org.alpine.invoice.invoicegem.inventory.repository.InventoryItemEntityRepository;
import org.alpine.invoice.invoicegem.product.ProductService;
import org.alpine.invoice.invoicegem.product.entity.CategoryEntity;
import org.alpine.invoice.invoicegem.product.entity.ProductEntity;
import org.alpine.invoice.invoicegem.product.repository.CategoryRepository;
import org.alpine.invoice.invoicegem.product.repository.ProductRepository;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderDto;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderLineItemDto;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseOrder;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseOrderLineItem;
import org.alpine.invoice.invoicegem.util.TestData;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.alpine.invoice.invoicegem.util.TestData.*;


@DataJpaTest
@Import({ProductService.class, InventoryService.class})
class PurchaseOrderServiceTest {


    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    InventoryItemEntityRepository inventoryItemEntityRepository;

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

        PurchaseOrderDto poDto = createPurchseOrderDto("test", CATEGORY_NAME);


        purchaseOrderService.createPurchaseOrder(poDto);


        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

        Assertions.assertThat(productRepository.findAll()).extracting(ProductEntity::getName).containsOnly("test");
        Assertions.assertThat(categoryRepository.findAll()).extracting(CategoryEntity::getName).containsOnly(CATEGORY_NAME);
        List<PurchaseOrder> allPo = purchaseOrderRepository.findAll();
        Assertions.assertThat(allPo).extracting(PurchaseOrder::getOrderNumber).containsOnly(poDto.getOrderNumber());
        Assertions.assertThat(allPo).
                satisfiesExactly( purchaseOrder ->
                        Assertions.assertThat(purchaseOrder.getPurchaseOrderLineItems())
                                .extracting(PurchaseOrderLineItem::getTotalPrice)
                .containsOnly(poDto.getLineItems().get(0).getTotalPrice()));

    }

    @Test
    void testProductAndCategoryDoesntGetCreatedFromPurchaseOrderWhenAlreadyExist() {

        PurchaseOrderDto poDto = createPurchseOrderDto("test", CATEGORY_NAME);
        poDto.setId(null);
        PurchaseOrderLineItemDto poLineItem = Instancio.create(PurchaseOrderLineItemDto.class);
        poDto.setLineItems(List.of(poLineItem));
        poLineItem.setProductName(PRODUCT_NAME);
        poLineItem.setCategoryName(CATEGORY_NAME);
        productRepository.save(TestData.createProductWithCategory("test", CATEGORY_NAME));


        purchaseOrderService.createPurchaseOrder(poDto);

        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

        Assertions.assertThat(productRepository.findAll()).extracting(ProductEntity::getName).containsOnly("test");
        Assertions.assertThat(categoryRepository.findAll()).extracting(CategoryEntity::getName).containsOnly(CATEGORY_NAME);
        List<PurchaseOrder> allPo = purchaseOrderRepository.findAll();
        Assertions.assertThat(allPo).extracting(PurchaseOrder::getOrderNumber).containsOnly(poDto.getOrderNumber());
        Assertions.assertThat(allPo).
                satisfiesExactly( purchaseOrder ->
                        Assertions.assertThat(purchaseOrder.getPurchaseOrderLineItems())
                                .extracting(PurchaseOrderLineItem::getTotalPrice)
                                .containsOnly(poLineItem.getTotalPrice()));

    }



    @Test
    void testPoItemGettingShelved() {

        PurchaseOrderDto purchseOrderDto = createPurchseOrderDto(PRODUCT_NAME, CATEGORY_NAME);
        Long poId = purchaseOrderService.createPurchaseOrder(purchseOrderDto);
        purchaseOrderService.shelvePurchasedItems(poId);
        Assertions.assertThat(inventoryItemEntityRepository.count()).isEqualTo(purchseOrderDto.getLineItems().size());



    }
}