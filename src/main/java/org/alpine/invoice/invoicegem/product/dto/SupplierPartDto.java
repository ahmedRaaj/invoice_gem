package org.alpine.invoice.invoicegem.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SupplierPartDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imagePath;
    private String categoryName;
}
