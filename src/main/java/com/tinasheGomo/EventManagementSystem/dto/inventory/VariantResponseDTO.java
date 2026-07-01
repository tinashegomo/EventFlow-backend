package com.tinasheGomo.EventManagementSystem.dto.inventory;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class VariantResponseDTO {
    private String id;
    private String sizeName;
    private BigDecimal pricePerUnit;
    private Integer quantityInStock;
    private LocalDateTime createdAt;
}
