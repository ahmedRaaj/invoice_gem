package org.alpine.invoice.invoicegem.purchase;

import org.alpine.invoice.invoicegem.inventory.InventoryService;
import org.alpine.invoice.invoicegem.inventory.entity.InventoryItemEntity;
import org.alpine.invoice.invoicegem.inventory.entity.InventoryItemTransactionEntity;
import org.alpine.invoice.invoicegem.inventory.repository.InventoryItemEntityRepository;
import org.alpine.invoice.invoicegem.inventory.util.InventoryTransactionStatus;
import org.alpine.invoice.invoicegem.product.ProductService;
import org.alpine.invoice.invoicegem.product.entity.CategoryEntity;
import org.alpine.invoice.invoicegem.product.entity.ProductEntity;
import org.alpine.invoice.invoicegem.product.repository.CategoryRepository;
import org.alpine.invoice.invoicegem.product.repository.ProductRepository;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderDto;
import org.alpine.invoice.invoicegem.purchase.dto.PurchaseOrderLineItemDto;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseInvoice;
import org.alpine.invoice.invoicegem.purchase.entity.PurchaseInvoiceLineItem;
import org.alpine.invoice.invoicegem.util.TestData;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.alpine.invoice.invoicegem.util.TestData.*;


@DataJpaTest
@Import({ProductService.class, InventoryService.class})
class PurchaseInvoiceServiceTest {


    @Autowired
    private PurchaseInvoiceRepository purchaseInvoiceRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    InventoryItemEntityRepository inventoryItemEntityRepository;

    private PurchaseInvoiceService purchaseInvoiceService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        purchaseInvoiceService = new PurchaseInvoiceService(purchaseInvoiceRepository,productService,inventoryService);
    }

    @Test
    void testProductAndCategoryGetsCreatedFromPurchaseOrder() {

        PurchaseOrderDto poDto = createPurchseOrderDto("test", CATEGORY_NAME);


        purchaseInvoiceService.createPurchaseOrder(poDto);


        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

        Assertions.assertThat(productRepository.findAll()).extracting(ProductEntity::getName).containsOnly("test");
        Assertions.assertThat(categoryRepository.findAll()).extracting(CategoryEntity::getName).containsOnly(CATEGORY_NAME);
        List<PurchaseInvoice> allPo = purchaseInvoiceRepository.findAll();
        Assertions.assertThat(allPo).extracting(PurchaseInvoice::getInvoiceNumber).containsOnly(poDto.getInvoiceNumber());
        Assertions.assertThat(allPo).
                satisfiesExactly( purchaseOrder ->
                        Assertions.assertThat(purchaseOrder.getPurchaseInvoiceLineItems())
                                .extracting(PurchaseInvoiceLineItem::getTotalPrice)
                .containsOnly(poDto.getLineItems().getFirst().getTotalPrice()));

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


        purchaseInvoiceService.createPurchaseOrder(poDto);

        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

        Assertions.assertThat(productRepository.findAll()).extracting(ProductEntity::getName).containsOnly("test");
        Assertions.assertThat(categoryRepository.findAll()).extracting(CategoryEntity::getName).containsOnly(CATEGORY_NAME);
        List<PurchaseInvoice> allPo = purchaseInvoiceRepository.findAll();
        Assertions.assertThat(allPo).extracting(PurchaseInvoice::getInvoiceNumber).containsOnly(poDto.getInvoiceNumber());
        Assertions.assertThat(allPo).
                satisfiesExactly( purchaseOrder ->
                        Assertions.assertThat(purchaseOrder.getPurchaseInvoiceLineItems())
                                .extracting(PurchaseInvoiceLineItem::getTotalPrice)
                                .containsOnly(poLineItem.getTotalPrice()));

    }



    @Test
    void testPoItemGettingShelved() {

        PurchaseOrderDto purchseOrderDto = createPurchseOrderDto(PRODUCT_NAME, CATEGORY_NAME);
        Long poId = purchaseInvoiceService.createPurchaseOrder(purchseOrderDto);
        purchaseInvoiceService.shelvePurchasedItems(poId);
        Assertions.assertThat(inventoryItemEntityRepository.count()).isEqualTo(1);
       Assertions.assertThat( purchaseInvoiceRepository.findById(poId).get().getStatus())
               .isEqualTo(PurchaseInvoiceStatus.GOODS_SHELVED);

        List<InventoryItemEntity> inventoryList = inventoryItemEntityRepository.findAll();
        Assertions.assertThat(inventoryList.getFirst().getInventoryItems())
               .extracting(InventoryItemTransactionEntity::getTransactionStatus)
               .containsOnly(InventoryTransactionStatus.APPROVED);

        BigDecimal inventoryUnitPrice = inventoryList.getFirst().getInventoryItems().getFirst()
                .getUnitPrice();
        Assertions.assertThat(inventoryUnitPrice)
                .isEqualTo(purchseOrderDto.getLineItems().getFirst().getUnitPrice());


    }
}