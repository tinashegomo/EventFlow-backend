package com.tinasheGomo.EventManagementSystem.dto.event;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class EventResponseDTO {
    private String id;
    private String title;
    private String eventTypeId;
    private String eventTypeName;
    private String status;
    private String clientName;
    private String clientPhone;
    private String clientEmail;
    private String venue;
    private String scheduledDate;
    private String scheduledTime;
    private Integer guestCount;
    private String priceMode;
    private String selectedPricingTierId;
    private BigDecimal customPrice;
    private BigDecimal totalPrice;
    private String notes;
    private String createdBy;
    private String updatedBy;
    private String organizationId;
    private List<AttachedItemResponseDTO> attachedItems;
    private List<PaymentResponseDTO> payments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
