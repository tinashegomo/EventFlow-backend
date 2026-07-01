package com.tinasheGomo.EventManagementSystem.controller.user;

import com.tinasheGomo.EventManagementSystem.dto.user.UpdateRoleRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.user.UserResponseDTO;
import com.tinasheGomo.EventManagementSystem.security.SecurityUtils;
import com.tinasheGomo.EventManagementSystem.service.audit.AuditService;
import com.tinasheGomo.EventManagementSystem.service.user.UserService;
import com.tinasheGomo.EventManagementSystem.enums.AuditAction;
import com.tinasheGomo.EventManagementSystem.enums.AuditEntityType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventflow/organizations/{orgId}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuditService auditService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getUsers(@PathVariable String orgId) {
        return ResponseEntity.ok(userService.getUsersByOrg(orgId));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String orgId, @PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateRole(
            @PathVariable String orgId,
            @PathVariable String userId,
            @Valid @RequestBody UpdateRoleRequestDTO request) {
        UserResponseDTO result = userService.updateUserRole(orgId, userId, request);
        var current = SecurityUtils.getCurrentUserEntity();
        auditService.log(
                orgId, current.getId(), current.getDisplayName(),
                request.getRole().name().equals("ADMIN") ? AuditAction.PROMOTED : AuditAction.DEMOTED,
                AuditEntityType.USER, userId, result.getDisplayName(),
                "Role changed to " + request.getRole(), null);
        return ResponseEntity.ok(result);
    }
}
