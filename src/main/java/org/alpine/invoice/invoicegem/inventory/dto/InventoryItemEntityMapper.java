package org.alpine.invoice.invoicegem.inventory.dto;

import org.alpine.invoice.invoicegem.inventory.entity.InventoryItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface InventoryItemEntityMapper {
    InventoryItemEntity toEntity(InventoryItemEntityDto inventoryItemEntityDto);

    InventoryItemEntityDto toDto(InventoryItemEntity inventoryItemEntity);

    InventoryItemEntity partialUpdate(InventoryItemEntityDto inventoryItemEntityDto, @MappingTarget InventoryItemEntity inventoryItemEntity);
}