package org.alpine.invoice.invoicegem.service;


import org.alpine.invoice.invoicegem.product.ProductService;
import org.alpine.invoice.invoicegem.product.dto.ProductDto;
import org.alpine.invoice.invoicegem.product.entity.CategoryEntity;
import org.alpine.invoice.invoicegem.product.entity.ProductEntity;
import org.alpine.invoice.invoicegem.product.repository.CategoryRepository;
import org.alpine.invoice.invoicegem.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductEntityServiceTest {
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

        ProductDto productDto = createProductDto();
        productDto.setId(null);

        ProductEntity productEntity = productService.insertProductAndCategoryFrom(productDto);

        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

        Assertions.assertThat(productEntity.getId()).isNotNull();
        Assertions.assertThat(productEntity.getCategoryEntity().getId()).isNotNull();
        Assertions.assertThat(productEntity.getCategoryEntity().getName()).isEqualTo(productDto.getCategoryName());

    }

    @Test
    void productWithOldCategoryNameInsertedOnlyProduct() {
        ProductDto productDto = createProductDto();
        categoryRepository.save(new CategoryEntity(productDto.getCategoryName()));

        ProductEntity productEntity = productService.insertProductAndCategoryFrom(productDto);

        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);
        Assertions.assertThat(productEntity.getId()).isNotNull();
        Assertions.assertThat(productEntity.getCategoryEntity().getId()).isNotNull();
        Assertions.assertThat(productEntity.getCategoryEntity().getName()).isEqualTo(productDto.getCategoryName());

    }

    @Test
    void productDeletedWithoutCategory() {
        ProductDto productDto = createProductDto();
        categoryRepository.save(new CategoryEntity(productDto.getCategoryName()));

        ProductEntity productEntity = productService.insertProductAndCategoryFrom(productDto);

        productService.deleteProduct(productEntity.getId());

        Assertions.assertThat(productRepository.count()).isEqualTo(0);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

    }

    private static ProductDto createProductDto() {
        ProductDto productDto = Instancio.create(ProductDto.class);
        productDto.setId(null);
        return productDto;
    }

    @Test
    void productInsertedWithoutCategory() {
        ProductDto productDto = createProductDto();
        productDto.setCategoryName(null);

        ProductEntity productEntity = productService.insertProductAndCategoryFrom(productDto);

        Assertions.assertThat(productRepository.count()).isEqualTo(1);
        Assertions.assertThat(categoryRepository.count()).isEqualTo(0);

    }
}