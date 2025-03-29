package ru.almaz.dailycalorieintake.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.almaz.dailycalorieintake.dto.AddDishRequest;
import ru.almaz.dailycalorieintake.dto.DishInfo;
import ru.almaz.dailycalorieintake.entity.Dish;
import ru.almaz.dailycalorieintake.service.DishService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;

    @PostMapping("/add")
    public void addDish(@RequestBody @Valid AddDishRequest addDishRequest) {
        dishService.addDish(addDishRequest);
    }

    @GetMapping
    public List<DishInfo> getAllDishes() {
        return dishService.getAllDishes();
    }
}
