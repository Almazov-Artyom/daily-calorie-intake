package ru.almaz.dailycalorieintake.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateHeightRequest {
    @NotNull(message = "Рост не может быть пустым")
    @Min(value = 60, message = "Рост должен быть не менее 60 см")
    @Max(value = 250, message = "Рост не может быть больше 250 см")
    private Double height;
}
