package org.alpine.invoice.invoicegem.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {
    private String address;
    private String phone;
    private String email;
    private String city;
    private String state;
    private String country;
}
