package ru.almaz.dailycalorieintake.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AddFoodIntakeRequest {
    @NotNull(message = "Список блюд не может быть пустым")
    private List<Long> dishesId;

    private LocalDate date;
}
