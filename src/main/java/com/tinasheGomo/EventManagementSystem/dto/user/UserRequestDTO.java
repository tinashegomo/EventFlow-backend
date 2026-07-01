package com.tinasheGomo.EventManagementSystem.dto.user;

import com.tinasheGomo.EventManagementSystem.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequestDTO {
    @NotBlank @Email
    private String email;

    @NotBlank
    private String displayName;

    private String phone;

    private UserRole role;
}
