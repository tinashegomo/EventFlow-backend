package com.tinasheGomo.EventManagementSystem.dto.quotation;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class LineItemResponseDTO {
    private String id;
    private String inventoryItemId;
    private String variantId;
    private Integer quantity;
    private String snapshotName;
    private String snapshotSize;
    private BigDecimal snapshotPrice;
    private LocalDateTime createdAt;
}
