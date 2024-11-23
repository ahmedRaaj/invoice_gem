package org.alpine.invoice.invoicegem.product.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.alpine.invoice.invoicegem.entity.AlpineEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class SupplierPart extends AlpineEntity {

    private String name;
    private String description;
    private String imagePath;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    private LocalDateTime createdAt = LocalDateTime.now();

    public SupplierPart(String name) {
        this.name = name;
    }
    public SupplierPart() {}

}
