package ru.almaz.dailycalorieintake.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.almaz.dailycalorieintake.dto.UpdateHeightRequest;
import ru.almaz.dailycalorieintake.entity.User;
import ru.almaz.dailycalorieintake.enums.Gender;
import ru.almaz.dailycalorieintake.enums.Purpose;
import ru.almaz.dailycalorieintake.exception.UserAlreadyExistException;
import ru.almaz.dailycalorieintake.exception.UserNotFoundException;
import ru.almaz.dailycalorieintake.mapper.UserMapper;
import ru.almaz.dailycalorieintake.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUser_success() {
        User user = new User();
        user.setAge(18);
        user.setHeight(180.0);
        user.setWeight(80.0);
        user.setGender(Gender.MALE);

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        userService.createUser(user);

        verify(userRepository).save(user);
    }

    @Test
    public void createUser_usernameAlreadyExists() {
        User user = new User();

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        UserAlreadyExistException userAlreadyExistException =
                assertThrows(UserAlreadyExistException.class, () -> userService.createUser(user));
        assertEquals("Пользователь с таким именем уже существует",userAlreadyExistException.getMessage());
    }

    @Test
    public void createUser_emailAlreadyExists() {
        User user = new User();

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        UserAlreadyExistException userAlreadyExistException =
                assertThrows(UserAlreadyExistException.class, () -> userService.createUser(user));
        assertEquals("Пользователь с таким email уже существует",userAlreadyExistException.getMessage());
    }

    @Test
    public void getUserByUsername_success() {
        User user = new User();
        user.setUsername("testUsername");

        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByUsername("testUsername");
        assertEquals(user,foundUser);
    }

    @Test
    public void getUserByUsername_userNotFound() {
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("testUsername"));
    }
}