package org.alpine.invoice.invoicegem.service;

import lombok.AllArgsConstructor;
import org.alpine.invoice.invoicegem.product.dto.ProductDto;
import org.alpine.invoice.invoicegem.product.dto.ProductMapper;
import org.alpine.invoice.invoicegem.product.entity.Category;
import org.alpine.invoice.invoicegem.product.entity.Product;
import org.alpine.invoice.invoicegem.repository.CategoryRepository;
import org.alpine.invoice.invoicegem.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public Product insertProductFrom(ProductDto productDto) {

        Product product = ProductMapper.INSTANCE.productDtoToProduct(productDto);
        String categoryName = productDto.getCategoryName();
        if(categoryName != null) {
            Optional<Category> category = categoryRepository.findTopByNameIgnoreCase(categoryName).or(() -> Optional.of(new Category(categoryName)));
            product.setCategory(category.get());
        }
       return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void insertProductRecordIfMissing(List<ProductDto> products) {
      products.forEach(this::insertProductFrom);
    }
}
