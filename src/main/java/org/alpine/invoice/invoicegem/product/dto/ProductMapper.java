package org.alpine.invoice.invoicegem.product.dto;

import org.alpine.invoice.invoicegem.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );
    Product productDtoToProduct(ProductDto productDto);
}
