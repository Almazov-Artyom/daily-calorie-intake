package ru.almaz.dailycalorieintake.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.almaz.dailycalorieintake.dto.UpdateAgeRequest;
import ru.almaz.dailycalorieintake.dto.UpdateHeightRequest;
import ru.almaz.dailycalorieintake.dto.UpdateWeightRequest;
import ru.almaz.dailycalorieintake.dto.UserInfo;
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

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_success() {
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
    void createUser_usernameAlreadyExists() {
        User user = new User();

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        UserAlreadyExistException userAlreadyExistException =
                assertThrows(UserAlreadyExistException.class, () -> userService.createUser(user));
        assertEquals("Пользователь с таким именем уже существует",userAlreadyExistException.getMessage());
    }

    @Test
    void createUser_emailAlreadyExists() {
        User user = new User();

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        UserAlreadyExistException userAlreadyExistException =
                assertThrows(UserAlreadyExistException.class, () -> userService.createUser(user));
        assertEquals("Пользователь с таким email уже существует",userAlreadyExistException.getMessage());
    }

    @Test
    void getUserByUsername_success() {
        User user = new User();
        user.setUsername("testUsername");

        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByUsername("testUsername");
        assertEquals(user,foundUser);
    }

    @Test
    void getUserByUsername_userNotFound() {
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("testUsername"));
    }

    @Test
    void updateHeight() {
        User user = new User();
        user.setHeight(200.0);
        user.setWeight(80.0);
        user.setGender(Gender.MALE);
        user.setAge(18);
        user.setDailyNorm(10.0);

        UpdateHeightRequest request = new UpdateHeightRequest();
        request.setHeight(180.0);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(userMapper.toUserInfo(user)).thenReturn(new UserInfo());

       userService.updateHeight(request);

        assertEquals(180.0, user.getHeight());
        assertNotEquals(10.0, user.getDailyNorm());

        verify(userRepository).save(user);
        verify(userMapper).toUserInfo(user);
    }

    @Test
    void updateWeight() {
        User user = new User();
        user.setHeight(200.0);
        user.setWeight(80.0);
        user.setGender(Gender.MALE);
        user.setAge(18);
        user.setDailyNorm(10.0);

        UpdateWeightRequest request = new UpdateWeightRequest();
        request.setWeight(100.0);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(userMapper.toUserInfo(user)).thenReturn(new UserInfo());

        userService.updateWeight(request);

        assertEquals(100.0,user.getWeight());
        assertNotEquals(10.0, user.getDailyNorm());

        verify(userRepository).save(user);
        verify(userMapper).toUserInfo(user);
    }

    @Test
    void updateAge() {
        User user = new User();
        user.setHeight(200.0);
        user.setWeight(80.0);
        user.setGender(Gender.MALE);
        user.setAge(18);
        user.setDailyNorm(10.0);

        UpdateAgeRequest request = new UpdateAgeRequest();
        request.setAge(19);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(userMapper.toUserInfo(user)).thenReturn(new UserInfo());

        userService.updateAge(request);

        assertEquals(19, user.getAge());
        assertNotEquals(10.0, user.getDailyNorm());

        verify(userRepository).save(user);
        verify(userMapper).toUserInfo(user);
    }



}