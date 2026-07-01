package com.tinasheGomo.EventManagementSystem.dto.event;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class AttachedItemResponseDTO {
    private String id;
    private String inventoryItemId;
    private String variantId;
    private Integer quantity;
    private String snapshotName;
    private String snapshotSize;
    private BigDecimal snapshotPrice;
    private LocalDateTime createdAt;
}
