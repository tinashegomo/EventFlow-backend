package com.tinasheGomo.EventManagementSystem.repository.audit;

import com.tinasheGomo.EventManagementSystem.entity.audit.AuditLogEntity;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import com.tinasheGomo.EventManagementSystem.enums.AuditAction;
import com.tinasheGomo.EventManagementSystem.enums.AuditEntityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, String> {
    Page<AuditLogEntity> findByOrganizationOrderByCreatedAtDesc(OrganizationEntity organization, Pageable pageable);
    Page<AuditLogEntity> findByOrganizationAndUserIdOrderByCreatedAtDesc(
            OrganizationEntity organization, String userId, Pageable pageable);
    Page<AuditLogEntity> findByOrganizationAndEntityTypeOrderByCreatedAtDesc(
            OrganizationEntity organization, AuditEntityType entityType, Pageable pageable);
    Page<AuditLogEntity> findByOrganizationAndActionOrderByCreatedAtDesc(
            OrganizationEntity organization, AuditAction action, Pageable pageable);
}
