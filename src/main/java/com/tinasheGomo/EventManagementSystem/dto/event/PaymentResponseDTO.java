package com.tinasheGomo.EventManagementSystem.dto.event;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class PaymentResponseDTO {
    private String id;
    private BigDecimal amount;
    private String date;
    private String note;
    private String recordedBy;
    private LocalDateTime createdAt;
}
