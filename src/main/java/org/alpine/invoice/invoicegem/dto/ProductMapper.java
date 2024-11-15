package org.alpine.invoice.invoicegem.dto;

import org.alpine.invoice.invoicegem.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );
    Product productDtoToProduct(ProductDto productDto);
}
