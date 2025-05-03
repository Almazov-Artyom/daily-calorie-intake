package ru.almaz.dailycalorieintake.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.dailycalorieintake.dto.UpdateAgeRequest;
import ru.almaz.dailycalorieintake.dto.UpdateHeightRequest;
import ru.almaz.dailycalorieintake.dto.UpdateWeightRequest;
import ru.almaz.dailycalorieintake.dto.UserInfo;
import ru.almaz.dailycalorieintake.entity.User;
import ru.almaz.dailycalorieintake.enums.Gender;
import ru.almaz.dailycalorieintake.exception.UserAlreadyExistException;
import ru.almaz.dailycalorieintake.exception.UserNotFoundException;
import ru.almaz.dailycalorieintake.mapper.UserMapper;
import ru.almaz.dailycalorieintake.repository.UserRepository;
import ru.almaz.dailycalorieintake.validator.UserValidator;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserValidator userValidator;

    private void formulaHarisBenedict(User user) {
        Double dailyNorm = 0.0;
        if (user.getGender() == Gender.MALE)
            dailyNorm = 88.36 + (13.4 * user.getWeight()) + (4.8 * user.getHeight()) - (5.7 * user.getAge());
        else
            dailyNorm = 447.6 + (9.2 * user.getWeight()) + (3.1 * user.getHeight()) - (4.3 * user.getAge());
        dailyNorm = Math.round(dailyNorm * 100.0) / 100.0;
        user.setDailyNorm(dailyNorm);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void createUser(User user) {
        userValidator.validateUser(user);
        formulaHarisBenedict(user);
        save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUsername(username);
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByUsername;
    }

    @Transactional
    public UserInfo updateHeight(UpdateHeightRequest request) {
        User user = getCurrentUser();
        user.setHeight(request.getHeight());
        formulaHarisBenedict(user);
        save(user);
        return userMapper.toUserInfo(user);
    }

    @Transactional
    public UserInfo updateWeight(UpdateWeightRequest request) {
        User user = getCurrentUser();
        user.setWeight(request.getWeight());
        formulaHarisBenedict(user);
        save(user);
        return userMapper.toUserInfo(user);
    }

    @Transactional
    public UserInfo updateAge(UpdateAgeRequest request) {
        User user = getCurrentUser();
        user.setAge(request.getAge());
        formulaHarisBenedict(user);
        save(user);
        return userMapper.toUserInfo(user);
    }
}
