package ru.almaz.dailycalorieintake;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.almaz.dailycalorieintake.dto.LoginRequest;
import ru.almaz.dailycalorieintake.dto.LoginResponse;
import ru.almaz.dailycalorieintake.dto.RegistrationRequest;
import ru.almaz.dailycalorieintake.dto.RegistrationResponse;
import ru.almaz.dailycalorieintake.entity.User;
import ru.almaz.dailycalorieintake.service.AuthService;

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

    @GetMapping("/id")
    public String id() {
        return  SecurityContextHolder.getContext().getAuthentication().getName();


    }

    @GetMapping("/auth")
    public String auth() {
        return "auth";
    }

}
