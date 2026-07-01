package com.tinasheGomo.EventManagementSystem.mapper.audit;

import com.tinasheGomo.EventManagementSystem.dto.audit.AuditLogResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.audit.AuditLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {
    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "entityType", target = "entityType")
    AuditLogResponseDTO toResponse(AuditLogEntity entity);
    List<AuditLogResponseDTO> toResponseList(List<AuditLogEntity> entities);
}
