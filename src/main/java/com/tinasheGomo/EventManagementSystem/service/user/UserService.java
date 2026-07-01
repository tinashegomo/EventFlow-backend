package com.tinasheGomo.EventManagementSystem.service.user;

import com.tinasheGomo.EventManagementSystem.dto.user.UpdateRoleRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.user.UserResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import com.tinasheGomo.EventManagementSystem.entity.user.UserEntity;
import com.tinasheGomo.EventManagementSystem.enums.UserRole;
import com.tinasheGomo.EventManagementSystem.exception.exceptions.BusinessRuleException;
import com.tinasheGomo.EventManagementSystem.exception.exceptions.NotFoundException;
import com.tinasheGomo.EventManagementSystem.mapper.user.UserMapper;
import com.tinasheGomo.EventManagementSystem.repository.organization.OrganizationRepository;
import com.tinasheGomo.EventManagementSystem.repository.user.UserRepository;
import com.tinasheGomo.EventManagementSystem.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final UserMapper userMapper;

    public UserResponseDTO getCurrentUser() {
        UserEntity user = SecurityUtils.getCurrentUserEntity();
        if (user == null) throw new NotFoundException("No authenticated user");
        return userMapper.toResponse(user);
    }

    public List<UserResponseDTO> getUsersByOrg(String orgId) {
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));
        return userMapper.toResponseList(userRepository.findByOrganization(org));
    }

    public UserResponseDTO getUserById(String userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));
        return userMapper.toResponse(user);
    }

    public UserResponseDTO updateUserRole(String orgId, String userId, UpdateRoleRequestDTO request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));

        if (request.getRole() == UserRole.STAFF) {
            long adminCount = userRepository.countByOrganizationAndRole(org, "ADMIN");
            if (adminCount <= 1) {
                throw new BusinessRuleException("Cannot demote the last admin");
            }
        }

        user.setRole(request.getRole());
        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }
}
