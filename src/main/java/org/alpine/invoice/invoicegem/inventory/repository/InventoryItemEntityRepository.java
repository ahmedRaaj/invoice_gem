package org.alpine.invoice.invoicegem.inventory.repository;

import org.alpine.invoice.invoicegem.inventory.entity.InventoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemEntityRepository extends JpaRepository<InventoryItemEntity, Long> {
}