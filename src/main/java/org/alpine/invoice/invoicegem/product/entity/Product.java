package org.alpine.invoice.invoicegem.product.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.alpine.invoice.invoicegem.entity.AlpineEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Product extends AlpineEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private String imagePath;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createdAt = LocalDateTime.now();
}
