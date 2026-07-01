package com.tinasheGomo.EventManagementSystem.dto.user;

import com.tinasheGomo.EventManagementSystem.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateRoleRequestDTO {
    @NotNull
    private UserRole role;
}
