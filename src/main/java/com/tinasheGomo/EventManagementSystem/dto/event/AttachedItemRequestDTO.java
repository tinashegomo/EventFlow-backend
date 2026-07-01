package com.tinasheGomo.EventManagementSystem.dto.event;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class AttachedItemRequestDTO {
    @NotBlank
    private String inventoryItemId;

    private String variantId;

    @NotNull @Min(1)
    private Integer quantity;

    @NotBlank
    private String snapshotName;

    private String snapshotSize;

    @NotNull
    private BigDecimal snapshotPrice;
}
