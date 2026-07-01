package com.tinasheGomo.EventManagementSystem.dto.organization;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class OrganizationResponseDTO {
    private String id;
    private String name;
    private String logo;
    private String ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
