package ru.almaz.dailycalorieintake.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.almaz.dailycalorieintake.entity.User;
import ru.almaz.dailycalorieintake.exception.UserAlreadyExistException;
import ru.almaz.dailycalorieintake.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с таким email уже существует");
        }
    }
}
