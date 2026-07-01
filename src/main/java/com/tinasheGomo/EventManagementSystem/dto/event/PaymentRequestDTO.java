package com.tinasheGomo.EventManagementSystem.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class PaymentRequestDTO {
    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String date;

    private String note;
}
