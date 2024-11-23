package org.alpine.invoice.invoicegem.product;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alpine.invoice.invoicegem.product.dto.SupplierPartDto;
import org.alpine.invoice.invoicegem.product.dto.SupplierPartMapper;
import org.alpine.invoice.invoicegem.product.entity.CategoryEntity;
import org.alpine.invoice.invoicegem.product.entity.SupplierPart;
import org.alpine.invoice.invoicegem.product.repository.CategoryRepository;
import org.alpine.invoice.invoicegem.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SupplierPartService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SupplierPart insertProductAndCategoryFrom(SupplierPartDto supplierPartDto) {

        Assert.isNull(supplierPartDto.getId(), "Product id must be null while inserting");
        log.info("Inserting product from {}", supplierPartDto);
        SupplierPart supplierPart = SupplierPartMapper.INSTANCE.productDtoToProduct(supplierPartDto);
        String categoryName = supplierPartDto.getCategoryName();
        if (categoryName != null) {
            Optional<CategoryEntity> category = categoryRepository.findTopByNameIgnoreCase(categoryName).or(() -> Optional.of(new CategoryEntity(categoryName)));
            supplierPart.setCategoryEntity(category.get());
        }
        SupplierPart saved = productRepository.save(supplierPart);
        return saved;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void insertProductRecordIfMissing(List<SupplierPartDto> products) {
        products.stream().filter(this::isThisNewProduct).forEach(this::insertProductAndCategoryFrom);
    }

    private boolean isThisNewProduct(SupplierPartDto dto) {
        boolean result = productRepository.existsByNameIgnoreCaseAndCategoryEntity_NameIgnoreCase(dto.getName(), dto.getCategoryName());

        return !result;

    }
}
