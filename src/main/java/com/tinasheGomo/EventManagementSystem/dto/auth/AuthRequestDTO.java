package com.tinasheGomo.EventManagementSystem.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequestDTO {
    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;
}
