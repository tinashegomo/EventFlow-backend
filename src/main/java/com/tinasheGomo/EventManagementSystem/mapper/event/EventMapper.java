package com.tinasheGomo.EventManagementSystem.mapper.event;

import com.tinasheGomo.EventManagementSystem.dto.event.AttachedItemResponseDTO;
import com.tinasheGomo.EventManagementSystem.dto.event.EventResponseDTO;
import com.tinasheGomo.EventManagementSystem.dto.event.PaymentResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.event.AttachedItemEntity;
import com.tinasheGomo.EventManagementSystem.entity.event.EventEntity;
import com.tinasheGomo.EventManagementSystem.entity.event.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "eventType.id", target = "eventTypeId")
    @Mapping(source = "eventType.name", target = "eventTypeName")
    @Mapping(source = "status", target = "status")
    EventResponseDTO toResponse(EventEntity entity);
    List<EventResponseDTO> toResponseList(List<EventEntity> entities);
    AttachedItemResponseDTO toAttachedItemResponse(AttachedItemEntity entity);
    List<AttachedItemResponseDTO> toAttachedItemResponseList(List<AttachedItemEntity> entities);
    PaymentResponseDTO toPaymentResponse(PaymentEntity entity);
    List<PaymentResponseDTO> toPaymentResponseList(List<PaymentEntity> entities);
}
