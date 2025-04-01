package ru.almaz.dailycalorieintake.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistrationRequest {
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Неправильный email")
    private String email;

    @NotNull(message = "Возраст не может быть пустым")
    @Min(value = 18, message = "Возраст должен быть не менее 18 лет")
    @Max(value = 65, message = "Возраст не должен быть более 65 лет")
    private Integer age;

    @NotNull(message = "Вес не может быть пустым")
    @DecimalMin(value = "10.0", message = "Вес должен быть не менее 10 кг")
    @DecimalMax(value = "400.0", message = "Вес не может быть больше 400 кг")
    private Double weight;

    @NotNull(message = "Рост не может быть пустым")
    @DecimalMin(value = "60.0", message = "Рост должен быть не менее 60 см")
    @DecimalMax(value = "250.0", message = "Рост не может быть больше 250 см")
    private Double height;

    @NotBlank(message = "Цель не может быть пустой")
    private String purpose;

    @NotBlank(message = "Пол не может быть пустым")
    private String gender;

}
