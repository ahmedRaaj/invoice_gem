package org.alpine.invoice.invoicegem.repository;

import org.alpine.invoice.invoicegem.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findTopByNameIgnoreCase(String name);
}
