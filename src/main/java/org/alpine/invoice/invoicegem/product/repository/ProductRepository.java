package org.alpine.invoice.invoicegem.product.repository;

import org.alpine.invoice.invoicegem.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    public boolean existsByNameIgnoreCaseAndCategoryEntity_NameIgnoreCase(String name, String categoryName);


}
