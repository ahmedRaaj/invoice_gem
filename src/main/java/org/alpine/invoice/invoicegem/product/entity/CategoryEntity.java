package org.alpine.invoice.invoicegem.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.alpine.invoice.invoicegem.entity.AlpineEntity;

import java.util.List;

@Entity
@Setter
@Getter
public class CategoryEntity extends AlpineEntity {

    private String name;
    private String description;
    @OneToMany(mappedBy = "id" )
    private List<SupplierPart> productEntities;

    public CategoryEntity() {
    }

    public CategoryEntity(String name) {
        this.name = name;
    }
}
