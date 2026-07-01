package com.tinasheGomo.EventManagementSystem.mapper.eventtype;

import com.tinasheGomo.EventManagementSystem.dto.eventtype.EventTypeRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.eventtype.EventTypeResponseDTO;
import com.tinasheGomo.EventManagementSystem.dto.eventtype.PricingTierResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.eventtype.EventTypeEntity;
import com.tinasheGomo.EventManagementSystem.entity.eventtype.PricingTierEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EventTypeMapper {
    @Mapping(source = "organization.id", target = "organizationId")
    EventTypeResponseDTO toResponse(EventTypeEntity entity);
    List<EventTypeResponseDTO> toResponseList(List<EventTypeEntity> entities);
    PricingTierResponseDTO toTierResponse(PricingTierEntity entity);
    List<PricingTierResponseDTO> toTierResponseList(List<PricingTierEntity> entities);
}
