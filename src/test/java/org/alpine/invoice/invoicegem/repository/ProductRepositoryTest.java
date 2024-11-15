package org.alpine.invoice.invoicegem.repository;

import org.alpine.invoice.invoicegem.entity.Category;
import org.alpine.invoice.invoicegem.entity.Product;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;


    @Test
    void recordInserted() {
        Product product = Instancio.create(Product.class);
        product.setId(null);
        Category category = Instancio.create(Category.class);
        category.setId(null);
        product.setCategory(category);

        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(productRepository.count()).isZero();

        productRepository.save(product);
        Assertions.assertThat(productRepository.count()).isOne();
    }



}