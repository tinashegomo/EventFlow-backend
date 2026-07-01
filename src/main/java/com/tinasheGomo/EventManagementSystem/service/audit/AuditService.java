package com.tinasheGomo.EventManagementSystem.service.audit;

import com.tinasheGomo.EventManagementSystem.entity.audit.AuditLogEntity;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import com.tinasheGomo.EventManagementSystem.enums.AuditAction;
import com.tinasheGomo.EventManagementSystem.enums.AuditEntityType;
import com.tinasheGomo.EventManagementSystem.exception.exceptions.NotFoundException;
import com.tinasheGomo.EventManagementSystem.repository.audit.AuditLogRepository;
import com.tinasheGomo.EventManagementSystem.repository.organization.OrganizationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;
    private final OrganizationRepository organizationRepository;
    private final ObjectMapper objectMapper;

    public void log(String orgId, String userId, String userName,
                    AuditAction action, AuditEntityType entityType,
                    String entityId, String entityName, String details, Object diff) {
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));
        AuditLogEntity log = new AuditLogEntity();
        log.setOrganization(org);
        log.setUserId(userId);
        log.setUserName(userName);
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setEntityName(entityName);
        log.setDetails(details);
        if (diff != null) {
            try {
                log.setDiff(objectMapper.writeValueAsString(diff));
            } catch (Exception e) {
                log.setDiff(diff.toString());
            }
        }
        auditLogRepository.save(log);
    }
}
