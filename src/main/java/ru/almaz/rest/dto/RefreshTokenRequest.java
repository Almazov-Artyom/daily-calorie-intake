package ru.almaz.rest.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
