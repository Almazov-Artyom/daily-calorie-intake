package ru.almaz.dailycalorieintake.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAgeRequest {
    @NotNull(message = "Возраст не может быть пустым")
    @Min(value = 18, message = "Возраст должен быть не менее 18 лет")
    @Max(value = 65, message = "Возраст не должен быть более 65 лет")
    private Integer age;
}
