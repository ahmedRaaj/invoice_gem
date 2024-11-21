package org.alpine.invoice.invoicegem.service;


import org.alpine.invoice.invoicegem.product.dto.ProductDto;
import org.alpine.invoice.invoicegem.product.entity.Category;
import org.alpine.invoice.invoicegem.product.entity.Product;
import org.alpine.invoice.invoicegem.repository.CategoryRepository;
import org.alpine.invoice.invoicegem.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductServiceTest {
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
              productService = new ProductService(productRepository,categoryRepository);
    }

    @Test
    void productWithNewCategoryInsertedTogether() {

        ProductDto productDto = Instancio.create(ProductDto.class);

        Product product = productService.insertProductFrom(productDto);

        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

        Assertions.assertThat(product.getId()).isNotNull();
        Assertions.assertThat(product.getCategory().getId()).isNotNull();
        Assertions.assertThat(product.getCategory().getName()).isEqualTo(productDto.getCategoryName());

    }

    @Test
    void productWithOldCategoryNameInsertedOnlyProduct() {
        ProductDto productDto = Instancio.create(ProductDto.class);
        categoryRepository.save(new Category(productDto.getCategoryName()));

        Product product = productService.insertProductFrom(productDto);

        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);
        Assertions.assertThat(product.getId()).isNotNull();
        Assertions.assertThat(product.getCategory().getId()).isNotNull();
        Assertions.assertThat(product.getCategory().getName()).isEqualTo(productDto.getCategoryName());

    }

    @Test
    void productDeletedWithoutCategory() {
        ProductDto productDto = Instancio.create(ProductDto.class);
        categoryRepository.save(new Category(productDto.getCategoryName()));

        Product product = productService.insertProductFrom(productDto);

        productService.deleteProduct(product.getId());

        Assertions.assertThat(productRepository.count()).isEqualTo(0);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

    }

    @Test
    void productInsertedWithoutCategory() {
        ProductDto productDto = Instancio.create(ProductDto.class);
        productDto.setCategoryName(null);

        Product product = productService.insertProductFrom(productDto);

        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(0);

    }
}