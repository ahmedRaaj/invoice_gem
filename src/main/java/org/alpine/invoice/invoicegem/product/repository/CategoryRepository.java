package org.alpine.invoice.invoicegem.product.repository;

import org.alpine.invoice.invoicegem.product.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findTopByNameIgnoreCase(String name);
}
