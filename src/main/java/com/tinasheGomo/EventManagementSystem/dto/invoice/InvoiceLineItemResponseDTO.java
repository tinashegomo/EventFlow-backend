package com.tinasheGomo.EventManagementSystem.dto.invoice;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class InvoiceLineItemResponseDTO {
    private String id;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private LocalDateTime createdAt;
}
