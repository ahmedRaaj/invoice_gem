package org.alpine.invoice.invoicegem.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Product extends AlpineEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private String imagePath;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;
}
