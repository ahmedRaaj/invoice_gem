package org.alpine.invoice.invoicegem.inventory.entity;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface InventoryItemEntityMapper {
    InventoryItemEntity toEntity(InventoryItemEntityDto inventoryItemEntityDto);

    InventoryItemEntityDto toDto(InventoryItemEntity inventoryItemEntity);

    InventoryItemEntity partialUpdate(InventoryItemEntityDto inventoryItemEntityDto, @MappingTarget InventoryItemEntity inventoryItemEntity);
}