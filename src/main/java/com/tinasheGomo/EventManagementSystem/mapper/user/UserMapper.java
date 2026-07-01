package com.tinasheGomo.EventManagementSystem.mapper.user;

import com.tinasheGomo.EventManagementSystem.dto.user.UserResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "organization.id", target = "organizationId")
    UserResponseDTO toResponse(UserEntity entity);
    List<UserResponseDTO> toResponseList(List<UserEntity> entities);
}
