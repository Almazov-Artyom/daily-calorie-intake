package ru.almaz.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.almaz.rest.dto.LoginRequest;
import ru.almaz.rest.dto.LoginResponse;
import ru.almaz.rest.dto.RegistrationRequest;
import ru.almaz.rest.dto.RegistrationResponse;
import ru.almaz.rest.service.AuthService;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class RestApplication {

    private final AuthService authService;

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @GetMapping("/non-auth")
    public String nonAuth() {
        return "non-auth";
    }

    @PostMapping("/register")
    public RegistrationResponse registration(@RequestBody RegistrationRequest registrationRequest) {
        System.out.println(registrationRequest);
        RegistrationResponse registrationResponse = authService.registration(registrationRequest);
        System.out.println(registrationResponse.toString());
        return registrationResponse;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("/auth")
    public String auth() {
        return "auth";
    }

}
