package com.tinasheGomo.EventManagementSystem.controller.audit;

import com.tinasheGomo.EventManagementSystem.dto.audit.AuditLogResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.audit.AuditLogEntity;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import com.tinasheGomo.EventManagementSystem.enums.AuditAction;
import com.tinasheGomo.EventManagementSystem.enums.AuditEntityType;
import com.tinasheGomo.EventManagementSystem.exception.exceptions.NotFoundException;
import com.tinasheGomo.EventManagementSystem.mapper.audit.AuditLogMapper;
import com.tinasheGomo.EventManagementSystem.repository.audit.AuditLogRepository;
import com.tinasheGomo.EventManagementSystem.repository.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eventflow/organizations/{orgId}/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;
    private final OrganizationRepository organizationRepository;
    private final AuditLogMapper auditLogMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AuditLogResponseDTO>> getLogs(
            @PathVariable String orgId,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) String action,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));

        Pageable pageable = PageRequest.of(page, Math.min(size, 200));
        Page<AuditLogEntity> logs;

        if (userId != null) {
            logs = auditLogRepository.findByOrganizationAndUserIdOrderByCreatedAtDesc(org, userId, pageable);
        } else if (entityType != null) {
            logs = auditLogRepository.findByOrganizationAndEntityTypeOrderByCreatedAtDesc(
                    org, AuditEntityType.valueOf(entityType), pageable);
        } else if (action != null) {
            logs = auditLogRepository.findByOrganizationAndActionOrderByCreatedAtDesc(
                    org, AuditAction.valueOf(action), pageable);
        } else {
            logs = auditLogRepository.findByOrganizationOrderByCreatedAtDesc(org, pageable);
        }

        return ResponseEntity.ok(logs.map(auditLogMapper::toResponse));
    }
}
