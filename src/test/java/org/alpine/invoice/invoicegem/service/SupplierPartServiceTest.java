package org.alpine.invoice.invoicegem.service;


import org.alpine.invoice.invoicegem.product.SupplierPartService;
import org.alpine.invoice.invoicegem.product.dto.SupplierPartDto;
import org.alpine.invoice.invoicegem.product.entity.CategoryEntity;
import org.alpine.invoice.invoicegem.product.entity.SupplierPart;
import org.alpine.invoice.invoicegem.product.repository.CategoryRepository;
import org.alpine.invoice.invoicegem.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SupplierPartServiceTest {
    private SupplierPartService supplierPartService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
              supplierPartService = new SupplierPartService(productRepository,categoryRepository);
    }

    @Test
    void productWithNewCategoryInsertedTogether() {

        SupplierPartDto supplierPartDto = createProductDto();
        supplierPartDto.setId(null);

        SupplierPart supplierPart = supplierPartService.insertProductAndCategoryFrom(supplierPartDto);

        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

        Assertions.assertThat(supplierPart.getId()).isNotNull();
        Assertions.assertThat(supplierPart.getCategoryEntity().getId()).isNotNull();
        Assertions.assertThat(supplierPart.getCategoryEntity().getName()).isEqualTo(supplierPartDto.getCategoryName());

    }

    @Test
    void productWithOldCategoryNameInsertedOnlyProduct() {
        SupplierPartDto supplierPartDto = createProductDto();
        categoryRepository.save(new CategoryEntity(supplierPartDto.getCategoryName()));

        SupplierPart supplierPart = supplierPartService.insertProductAndCategoryFrom(supplierPartDto);

        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);
        Assertions.assertThat(supplierPart.getId()).isNotNull();
        Assertions.assertThat(supplierPart.getCategoryEntity().getId()).isNotNull();
        Assertions.assertThat(supplierPart.getCategoryEntity().getName()).isEqualTo(supplierPartDto.getCategoryName());

    }

    @Test
    void productDeletedWithoutCategory() {
        SupplierPartDto supplierPartDto = createProductDto();
        categoryRepository.save(new CategoryEntity(supplierPartDto.getCategoryName()));

        SupplierPart supplierPart = supplierPartService.insertProductAndCategoryFrom(supplierPartDto);

        supplierPartService.deleteProduct(supplierPart.getId());

        Assertions.assertThat(productRepository.count()).isEqualTo(0);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

    }

    private static SupplierPartDto createProductDto() {
        SupplierPartDto supplierPartDto = Instancio.create(SupplierPartDto.class);
        supplierPartDto.setId(null);
        return supplierPartDto;
    }

    @Test
    void productInsertedWithoutCategory() {
        SupplierPartDto supplierPartDto = createProductDto();
        supplierPartDto.setCategoryName(null);

        SupplierPart supplierPart = supplierPartService.insertProductAndCategoryFrom(supplierPartDto);

        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(0);

    }
}