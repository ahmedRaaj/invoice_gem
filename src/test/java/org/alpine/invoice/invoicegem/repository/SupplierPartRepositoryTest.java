package org.alpine.invoice.invoicegem.repository;

import org.alpine.invoice.invoicegem.product.entity.CategoryEntity;
import org.alpine.invoice.invoicegem.product.entity.SupplierPart;
import org.alpine.invoice.invoicegem.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SupplierPartRepositoryTest {

    @Autowired
    ProductRepository productRepository;


    @Test
    void recordInserted() {
        SupplierPart supplierPart = Instancio.create(SupplierPart.class);
        supplierPart.setId(null);
        CategoryEntity categoryEntity = Instancio.create(CategoryEntity.class);
        categoryEntity.setId(null);
        supplierPart.setCategoryEntity(categoryEntity);

        Assertions.assertThat(supplierPart).isNotNull();
        Assertions.assertThat(productRepository.count()).isZero();

        productRepository.save(supplierPart);
        Assertions.assertThat(productRepository.count()).isOne();
    }



}