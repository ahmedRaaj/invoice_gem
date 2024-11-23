package org.alpine.invoice.invoicegem.repository;

import org.alpine.invoice.invoicegem.product.repository.ProductRepository;
import org.alpine.invoice.invoicegem.product.entity.CategoryEntity;
import org.alpine.invoice.invoicegem.product.entity.ProductEntity;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductEntityRepositoryTest {

    @Autowired
    ProductRepository productRepository;


    @Test
    void recordInserted() {
        ProductEntity productEntity = Instancio.create(ProductEntity.class);
        productEntity.setId(null);
        CategoryEntity categoryEntity = Instancio.create(CategoryEntity.class);
        categoryEntity.setId(null);
        productEntity.setCategoryEntity(categoryEntity);

        Assertions.assertThat(productEntity).isNotNull();
        Assertions.assertThat(productRepository.count()).isZero();

        productRepository.save(productEntity);
        Assertions.assertThat(productRepository.count()).isOne();
    }



}