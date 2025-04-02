package ru.almaz.dailycalorieintake.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CheckDailyNorm {
    private LocalDate date;

    private String message;

    private Double dailyNorm;

    private Double caloriesPerDay;
}
