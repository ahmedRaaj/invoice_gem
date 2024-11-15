package org.alpine.invoice.invoicegem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private String imagePath;
    private String categoryName;
}
