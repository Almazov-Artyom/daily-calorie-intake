package ru.almaz.dailycalorieintake.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddDishRequest {
    @NotBlank(message = "Имя блюда не может быть пустым")
    private String name;

    @NotNull
    @DecimalMin(value = "0.0", message = "Количество калорий не может быть отрицателым")
    private Double calories;

    @NotNull
    @DecimalMin(value = "0.0", message = "Количество белков не может быть отрицателым")
    private Double protein;

    @NotNull
    @DecimalMin(value = "0.0", message = "Количество жиров не может быть отрицателым")
    private Double fats;

    @NotNull
    @DecimalMin(value = "0.0", message = "Количество углеводов не может быть отрицателым")
    private Double carbs;
}
