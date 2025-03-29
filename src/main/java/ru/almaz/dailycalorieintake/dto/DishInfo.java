package ru.almaz.dailycalorieintake.dto;

import lombok.Data;

@Data
public class DishInfo {
    private String name;

    private Double calories;

    private Double protein;

    private Double fats;

    private Double carbs;
}
