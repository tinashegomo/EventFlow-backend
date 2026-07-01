package com.tinasheGomo.EventManagementSystem.dto.user;

import com.tinasheGomo.EventManagementSystem.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class UserResponseDTO {
    private String id;
    private String uid;
    private String email;
    private String displayName;
    private String photoURL;
    private String phone;
    private UserRole role;
    private String organizationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
