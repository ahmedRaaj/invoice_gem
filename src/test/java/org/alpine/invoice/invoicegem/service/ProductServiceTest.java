package org.alpine.invoice.invoicegem.service;


import org.alpine.invoice.invoicegem.dto.ProductDto;
import org.alpine.invoice.invoicegem.dto.ProductMapper;
import org.alpine.invoice.invoicegem.entity.Category;
import org.alpine.invoice.invoicegem.entity.Product;
import org.alpine.invoice.invoicegem.repository.CategoryRepository;
import org.alpine.invoice.invoicegem.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

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
}