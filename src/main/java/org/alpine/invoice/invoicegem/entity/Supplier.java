package org.alpine.invoice.invoicegem.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Supplier extends AlpineEntity {

    private String name;
    @Embedded
    private Address address;


}
