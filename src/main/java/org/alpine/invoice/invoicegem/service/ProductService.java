package org.alpine.invoice.invoicegem.service;

import lombok.AllArgsConstructor;
import org.alpine.invoice.invoicegem.dto.ProductDto;
import org.alpine.invoice.invoicegem.dto.ProductMapper;
import org.alpine.invoice.invoicegem.entity.Category;
import org.alpine.invoice.invoicegem.entity.Product;
import org.alpine.invoice.invoicegem.repository.CategoryRepository;
import org.alpine.invoice.invoicegem.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public Product insertProductFrom(ProductDto productDto) {

        Product product = ProductMapper.INSTANCE.productDtoToProduct(productDto);
        String categoryName = productDto.getCategoryName();
        Optional<Category> category = categoryRepository.findByNameIgnoreCase(categoryName)
                .or(() -> Optional.of(new Category(categoryName)));

        product.setCategory(category.get());
       return productRepository.save(product);
    }
}
