package com.tinasheGomo.EventManagementSystem.mapper.quotation;

import com.tinasheGomo.EventManagementSystem.dto.quotation.LineItemResponseDTO;
import com.tinasheGomo.EventManagementSystem.dto.quotation.QuotationResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.quotation.LineItemEntity;
import com.tinasheGomo.EventManagementSystem.entity.quotation.QuotationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface QuotationMapper {
    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "status", target = "status")
    QuotationResponseDTO toResponse(QuotationEntity entity);
    List<QuotationResponseDTO> toResponseList(List<QuotationEntity> entities);
    LineItemResponseDTO toLineItemResponse(LineItemEntity entity);
    List<LineItemResponseDTO> toLineItemResponseList(List<LineItemEntity> entities);
}
