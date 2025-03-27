package ru.almaz.rest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.almaz.rest.dto.*;
import ru.almaz.rest.entity.User;
import ru.almaz.rest.exception.InvalidRefreshTokenException;
import ru.almaz.rest.exception.UserNotFoundException;
import ru.almaz.rest.exception.UserUnauthenticatedException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public RegistrationResponse registration(RegistrationRequest registrationRequest) throws UserNotFoundException {
        System.out.println("Registration request: " + registrationRequest);
        User user = User.builder()
                .username(registrationRequest.getUsername())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();
        userService.save(user);
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
            return new LoginResponse(accessToken, refreshToken);
        }
        catch (AuthenticationException ex){
            throw new UserUnauthenticatedException("User unauthenticated");
        }
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String userName = jwtService.extractUserName(refreshToken);

        UserDetails userDetails = userService.getUserByUsername(userName);
        String accessToken;
        if(jwtService.isTokenValid(refreshToken, userDetails)) {
            accessToken = jwtService.generateAccessToken(userDetails);
        }
        else{
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
