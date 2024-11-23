package org.alpine.invoice.invoicegem.product;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alpine.invoice.invoicegem.product.dto.ProductDto;
import org.alpine.invoice.invoicegem.product.dto.ProductMapper;
import org.alpine.invoice.invoicegem.product.entity.CategoryEntity;
import org.alpine.invoice.invoicegem.product.entity.ProductEntity;
import org.alpine.invoice.invoicegem.product.repository.CategoryRepository;
import org.alpine.invoice.invoicegem.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductEntity insertProductAndCategoryFrom(ProductDto productDto) {

        Assert.isNull(productDto.getId(), "Product id must be null while inserting");
        log.info("Inserting product from {}", productDto);
        ProductEntity productEntity = ProductMapper.INSTANCE.productDtoToProduct(productDto);
        String categoryName = productDto.getCategoryName();
        if (categoryName != null) {
            Optional<CategoryEntity> category = categoryRepository.findTopByNameIgnoreCase(categoryName).or(() -> Optional.of(new CategoryEntity(categoryName)));
            productEntity.setCategoryEntity(category.get());
        }
        ProductEntity saved = productRepository.save(productEntity);
        return saved;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void insertProductRecordIfMissing(List<ProductDto> products) {
        products.stream().filter(dto->isThisNewProduct(dto)).forEach(this::insertProductAndCategoryFrom);
    }

    private boolean isThisNewProduct(ProductDto dto) {
        boolean result = productRepository.existsByNameIgnoreCaseAndCategoryEntity_NameIgnoreCase(dto.getName(), dto.getCategoryName());

        return !result;

    }
}
