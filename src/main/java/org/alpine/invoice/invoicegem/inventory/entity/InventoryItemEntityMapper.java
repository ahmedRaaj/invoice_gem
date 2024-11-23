package org.alpine.invoice.invoicegem.inventory.entity;

import org.mapstruct.*;

@Mapper
public interface InventoryItemEntityMapper {
    InventoryItemEntity toEntity(InventoryItemEntityDto inventoryItemEntityDto);

    InventoryItemEntityDto toDto(InventoryItemEntity inventoryItemEntity);

    InventoryItemEntity partialUpdate(InventoryItemEntityDto inventoryItemEntityDto, @MappingTarget InventoryItemEntity inventoryItemEntity);
}