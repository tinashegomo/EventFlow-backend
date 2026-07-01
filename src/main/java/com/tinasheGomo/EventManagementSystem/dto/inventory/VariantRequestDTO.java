package com.tinasheGomo.EventManagementSystem.dto.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class VariantRequestDTO {
    private String sizeName;

    @NotNull @Min(0)
    private BigDecimal pricePerUnit;

    @NotNull @Min(0)
    private Integer quantityInStock;
}
