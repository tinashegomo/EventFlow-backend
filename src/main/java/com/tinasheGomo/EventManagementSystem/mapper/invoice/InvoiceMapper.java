package com.tinasheGomo.EventManagementSystem.mapper.invoice;

import com.tinasheGomo.EventManagementSystem.dto.invoice.InvoiceLineItemResponseDTO;
import com.tinasheGomo.EventManagementSystem.dto.invoice.InvoiceResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.invoice.InvoiceEntity;
import com.tinasheGomo.EventManagementSystem.entity.invoice.InvoiceLineItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "quotation.id", target = "quotationId")
    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "status", target = "status")
    InvoiceResponseDTO toResponse(InvoiceEntity entity);
    List<InvoiceResponseDTO> toResponseList(List<InvoiceEntity> entities);
    InvoiceLineItemResponseDTO toLineItemResponse(InvoiceLineItemEntity entity);
    List<InvoiceLineItemResponseDTO> toLineItemResponseList(List<InvoiceLineItemEntity> entities);
}
