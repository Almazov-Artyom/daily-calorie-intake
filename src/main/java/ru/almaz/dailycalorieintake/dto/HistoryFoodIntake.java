package ru.almaz.dailycalorieintake.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class HistoryFoodIntake {
    private Map<LocalDate, List<DishInfoForHistoryFoodIntake>> dateAndDishes;

    @Data
    public static class DishInfoForHistoryFoodIntake {
        private String dishName;

        private Double calories;

        private Double protein;

        private Double fats;

        private Double carbs;
    }

}
