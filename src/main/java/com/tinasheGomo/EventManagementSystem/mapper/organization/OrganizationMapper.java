package com.tinasheGomo.EventManagementSystem.mapper.organization;

import com.tinasheGomo.EventManagementSystem.dto.organization.OrganizationRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.organization.OrganizationResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    OrganizationResponseDTO toResponse(OrganizationEntity entity);
    List<OrganizationResponseDTO> toResponseList(List<OrganizationEntity> entities);
    void updateFromDTO(OrganizationRequestDTO dto, @MappingTarget OrganizationEntity entity);
}
