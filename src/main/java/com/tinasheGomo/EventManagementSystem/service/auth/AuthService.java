package com.tinasheGomo.EventManagementSystem.service.auth;

import com.tinasheGomo.EventManagementSystem.dto.auth.AuthRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.auth.AuthResponseDTO;
import com.tinasheGomo.EventManagementSystem.dto.auth.RegisterRequestDTO;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import com.tinasheGomo.EventManagementSystem.entity.user.UserEntity;
import com.tinasheGomo.EventManagementSystem.enums.UserRole;
import com.tinasheGomo.EventManagementSystem.exception.exceptions.DuplicateException;
import com.tinasheGomo.EventManagementSystem.repository.organization.OrganizationRepository;
import com.tinasheGomo.EventManagementSystem.repository.user.UserRepository;
import com.tinasheGomo.EventManagementSystem.security.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("Email already registered: " + request.getEmail());
        }

        OrganizationEntity org = new OrganizationEntity();
        org.setName(request.getOrgName());
        org = organizationRepository.save(org);

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setDisplayName(request.getFullName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.ADMIN);
        user.setPhone(request.getPhone());
        user.setUid(java.util.UUID.randomUUID().toString());
        user.setOrganization(org);
        user = userRepository.save(user);

        org.setOwnerId(user.getId());
        organizationRepository.save(org);

        String token = jwtUtils.generateToken(user.getEmail());

        return new AuthResponseDTO(token, user.getId(), user.getEmail(),
                user.getDisplayName(), user.getRole().name(), org.getId());
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtils.generateToken(user.getEmail());

        return new AuthResponseDTO(token, user.getId(), user.getEmail(),
                user.getDisplayName(), user.getRole().name(), user.getOrganization().getId());
    }
}
