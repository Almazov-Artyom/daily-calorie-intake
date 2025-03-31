package ru.almaz.dailycalorieintake.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.almaz.dailycalorieintake.dto.RegistrationRequest;
import ru.almaz.dailycalorieintake.dto.RegistrationResponse;
import ru.almaz.dailycalorieintake.entity.User;
import ru.almaz.dailycalorieintake.exception.InvalidGenderException;
import ru.almaz.dailycalorieintake.exception.InvalidPurposeException;
import ru.almaz.dailycalorieintake.mapper.UserMapper;
import ru.almaz.dailycalorieintake.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registration_success() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setPurpose("loss");
        registrationRequest.setGender("male");

        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");

        when(userMapper.toUser(registrationRequest)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodePassword");

        RegistrationResponse registrationResponse = authService.registration(registrationRequest);

        assertEquals("testUsername", registrationResponse.getUsername());
        verify(userService).createUser(user);
    }

    @Test
    public void registration_invalidPurpose() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setPurpose("invalidPurpose");

        assertThrows(InvalidPurposeException.class, () -> authService.registration(registrationRequest));
    }

    @Test
    public void registration_invalidGender() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setPurpose("loss");
        registrationRequest.setGender("invalidGender");

        assertThrows(InvalidGenderException.class,() -> authService.registration(registrationRequest));
    }





}