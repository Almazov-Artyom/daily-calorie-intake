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
import ru.almaz.dailycalorieintake.validator.JwtValidator;
import ru.almaz.dailycalorieintake.validator.UserValidator;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    private final JwtCacheService jwtCacheService;

    private final JwtValidator jwtValidator;

    private final VerificationCacheService verificationCacheService;

    private final MailService mailService;

    private final UserValidator userValidator;

    public RegistrationResponse registration(RegistrationRequest registrationRequest) {
        userValidator.validateUser(registrationRequest.getUsername(), registrationRequest.getEmail());
        userValidator.validatePurpose(registrationRequest.getPurpose());
        userValidator.validateGender(registrationRequest.getGender());

        registrationRequest.setPurpose(registrationRequest.getPurpose().toUpperCase());
        registrationRequest.setGender(registrationRequest.getGender().toUpperCase());

        User user = userMapper.toUser(registrationRequest);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String uuid = UUID.randomUUID().toString();

        verificationCacheService.putUser(uuid, user);

        mailService.sendVerifyLink(user.getEmail(), uuid);

        return new RegistrationResponse("Вам на почту выслана ссылка для подтверждения email");
    }

    public VerificationDTO verifyEmail(String uuid){
        User user = verificationCacheService.getUser(uuid);

        if (user == null) {
            throw new VerifyEmailException("Email не подтвержден");
        }

        userService.createUser(user);

        verificationCacheService.removeUser(uuid);

        return new VerificationDTO("Вы успешно подвердили свой email");
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
            jwtCacheService.putRefreshToken(user.getUsername(), refreshToken);

            return new LoginResponse(accessToken, refreshToken);
        } catch (AuthenticationException ex) {
            throw new UserUnauthenticatedException("Пользователь не аутентифицирован");
        }
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String userName = jwtService.extractUserName(refreshToken);

        jwtValidator.refreshTokenValid(userName, refreshToken);

        UserDetails userDetails = userService.getUserByUsername(userName);

        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        jwtCacheService.putAccessToken(userName, newAccessToken);
        jwtCacheService.putRefreshToken(userName, newRefreshToken);

        return RefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
