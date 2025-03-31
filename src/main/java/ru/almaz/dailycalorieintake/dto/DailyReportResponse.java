package ru.almaz.dailycalorieintake.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DailyReportResponse {
    private LocalDate date;

    private Double sumAllCalories;

    private List<DishInfoForDailyReport> dishes;

    @Data
    public static class DishInfoForDailyReport {
        private String dishName;
        private Double calories;
    }
}
