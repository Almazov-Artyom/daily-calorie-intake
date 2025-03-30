package ru.almaz.dailycalorieintake.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckDailyNorm {
    private String message;

    private Double dailyNorm;

    private Double caloriesPerDay;
}
