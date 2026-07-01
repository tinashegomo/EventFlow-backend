package com.tinasheGomo.EventManagementSystem.controller.auth;

import com.tinasheGomo.EventManagementSystem.dto.auth.AuthRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.auth.AuthResponseDTO;
import com.tinasheGomo.EventManagementSystem.dto.auth.RegisterRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.user.UserResponseDTO;
import com.tinasheGomo.EventManagementSystem.service.auth.AuthService;
import com.tinasheGomo.EventManagementSystem.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eventflow/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }
}
