package ru.almaz.dailycalorieintake.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.almaz.dailycalorieintake.dto.*;
import ru.almaz.dailycalorieintake.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public RegistrationResponse registration(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return authService.registration(registrationRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public RefreshTokenResponse refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/verify/{uuid}")
    public VerificationDTO verifyEmail(@PathVariable String uuid) {
        return authService.verifyEmail(uuid);
    }
}
