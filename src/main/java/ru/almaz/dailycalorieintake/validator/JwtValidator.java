package ru.almaz.dailycalorieintake.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.almaz.dailycalorieintake.exception.InvalidAccessTokenException;
import ru.almaz.dailycalorieintake.exception.InvalidRefreshTokenException;
import ru.almaz.dailycalorieintake.service.JwtCacheService;

@Component
@RequiredArgsConstructor
public class JwtValidator {
    private final JwtCacheService jwtCacheService;

    public void accessTokenValid(String username, String token) {
        String tokenFromCache = jwtCacheService.getAccessToken(username);
        if(!token.equals(tokenFromCache)) {
            throw new InvalidAccessTokenException("Access токен невалидный");
        }
    }

    public void refreshTokenValid(String username, String token) {
        String tokenFromCache = jwtCacheService.getRefreshToken(username);
        if(!token.equals(tokenFromCache)) {
            throw new InvalidRefreshTokenException("Refresh токен невалидный");
        }
    }
}
