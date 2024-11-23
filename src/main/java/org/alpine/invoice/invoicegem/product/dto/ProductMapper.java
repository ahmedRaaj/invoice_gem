package org.alpine.invoice.invoicegem.product.dto;

import org.alpine.invoice.invoicegem.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );
    ProductEntity productDtoToProduct(ProductDto productDto);
}
