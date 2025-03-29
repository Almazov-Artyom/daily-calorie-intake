package ru.almaz.dailycalorieintake.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "RefreshToken не может быть пустым")
    private String refreshToken;
}
