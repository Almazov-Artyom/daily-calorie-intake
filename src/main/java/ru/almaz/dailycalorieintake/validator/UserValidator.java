package ru.almaz.dailycalorieintake.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.almaz.dailycalorieintake.entity.User;
import ru.almaz.dailycalorieintake.enums.Gender;
import ru.almaz.dailycalorieintake.enums.Purpose;
import ru.almaz.dailycalorieintake.exception.InvalidGenderException;
import ru.almaz.dailycalorieintake.exception.InvalidPurposeException;
import ru.almaz.dailycalorieintake.exception.UserAlreadyExistException;
import ru.almaz.dailycalorieintake.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateUser(String username, String userEmail) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(userEmail)) {
            throw new UserAlreadyExistException("Пользователь с таким email уже существует");
        }
    }

    public void validatePurpose(String purpose) {
        try {
            Purpose.valueOf(purpose.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidPurposeException("Неправильное значение цели");
        }
    }

    public void validateGender(String gender) {
        try {
            Gender.valueOf(gender.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new InvalidGenderException("Неправильное значение пола");

        }
    }
}
