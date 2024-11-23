package org.alpine.invoice.invoicegem.product.dto;

import org.alpine.invoice.invoicegem.product.entity.SupplierPart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SupplierPartMapper {

    SupplierPartMapper INSTANCE = Mappers.getMapper( SupplierPartMapper.class );
    SupplierPart productDtoToProduct(SupplierPartDto supplierPartDto);
}
