package com.tinasheGomo.EventManagementSystem.dto.event;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
public class EventRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String eventTypeId;

    @NotBlank
    private String clientName;

    private String clientPhone;
    private String clientEmail;

    @NotBlank
    private String venue;

    @NotBlank
    private String scheduledDate;

    @NotBlank
    private String scheduledTime;

    @NotNull @Min(1)
    private Integer guestCount;

    @NotBlank
    private String priceMode;

    private String selectedPricingTierId;

    private BigDecimal customPrice;

    private String status;

    private BigDecimal initialDeposit;

    private String notes;

    private List<AttachedItemRequestDTO> attachedItems;
}
