package com.tinasheGomo.EventManagementSystem.dto.audit;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class AuditLogResponseDTO {
    private String id;
    private String organizationId;
    private String userId;
    private String userName;
    private String action;
    private String entityType;
    private String entityId;
    private String entityName;
    private String details;
    private String diff;
    private LocalDateTime createdAt;
}
