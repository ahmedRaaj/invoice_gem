package org.alpine.invoice.invoicegem.product.repository;

import org.alpine.invoice.invoicegem.product.entity.SupplierPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<SupplierPart,Long> {

    public boolean existsByNameIgnoreCaseAndCategoryEntity_NameIgnoreCase(String name, String categoryName);


}
