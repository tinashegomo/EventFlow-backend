package com.tinasheGomo.EventManagementSystem.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String userId;
    private String email;
    private String displayName;
    private String role;
    private String organizationId;
}
