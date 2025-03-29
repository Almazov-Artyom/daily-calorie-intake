package ru.almaz.dailycalorieintake.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateWeightRequest {
    @NotNull(message = "Вес не может быть пустым")
    @Min(value = 10, message = "Вес должен быть не менее 10 кг")
    @Max(value = 400, message = "Вес не может быть больше 400 кг")
    private Double weight;
}
