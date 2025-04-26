package ru.almaz.dailycalorieintake.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.almaz.dailycalorieintake.dto.*;
import ru.almaz.dailycalorieintake.entity.User;
import ru.almaz.dailycalorieintake.enums.Gender;
import ru.almaz.dailycalorieintake.enums.Purpose;
import ru.almaz.dailycalorieintake.exception.*;
import ru.almaz.dailycalorieintake.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    private final JwtCacheService jwtCacheService;

    public RegistrationResponse registration(RegistrationRequest registrationRequest) {
        try {
            Purpose.valueOf(registrationRequest.getPurpose().toUpperCase());
            registrationRequest.setPurpose(registrationRequest.getPurpose().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidPurposeException("Неправильное значение цели");
        }
        try {
            Gender.valueOf(registrationRequest.getGender().toUpperCase());
            registrationRequest.setGender(registrationRequest.getGender().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidGenderException("Неправильное значение пола");
        }

        User user = userMapper.toUser(registrationRequest);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.createUser(user);

        return new RegistrationResponse(user.getUsername());
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            jwtCacheService.putAccessToken(user.getUsername(), accessToken);
            return new LoginResponse(accessToken, refreshToken);
        } catch (AuthenticationException ex) {
            throw new UserUnauthenticatedException("Пользователь не аутентифицирован");
        }
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String userName = jwtService.extractUserName(refreshToken);

        UserDetails userDetails = userService.getUserByUsername(userName);
        String accessToken;
        if (jwtService.isTokenValid(refreshToken)) {
            accessToken = jwtService.generateAccessToken(userDetails);
        } else {
            throw new InvalidRefreshTokenException("Невалидный refresh токен");
        }

        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
