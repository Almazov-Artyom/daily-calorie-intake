package ru.almaz.dailycalorieintake.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.almaz.dailycalorieintake.dto.AddDishRequest;
import ru.almaz.dailycalorieintake.dto.DishInfo;
import ru.almaz.dailycalorieintake.entity.Dish;
import ru.almaz.dailycalorieintake.exception.DishAlreadyExist;
import ru.almaz.dailycalorieintake.mapper.DishMapper;
import ru.almaz.dailycalorieintake.repository.DishRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {
    @Mock
    private DishRepository dishRepository;

    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private DishService dishService;

    @Test
    void addDish_success() {
        AddDishRequest addDishRequest = new AddDishRequest();
        addDishRequest.setName("pasta");

        Dish dish = new Dish();

        when(dishRepository.existsByName(addDishRequest.getName())).thenReturn(false);
        when(dishMapper.toDish(addDishRequest)).thenReturn(dish);

        dishService.addDish(addDishRequest);

        verify(dishRepository).save(dish);
    }

    @Test
    void addDish_fail() {
        AddDishRequest addDishRequest = new AddDishRequest();
        addDishRequest.setName("pasta");

        when(dishRepository.existsByName(addDishRequest.getName())).thenReturn(true);

        assertThrows(DishAlreadyExist.class, () -> dishService.addDish(addDishRequest));
    }

    @Test
    void getAllDish(){
        Dish dish = new Dish();
        dish.setName("pasta");

        DishInfo dishInfo = new DishInfo();
        dishInfo.setName("pasta");

        when(dishRepository.findAll()).thenReturn(List.of(dish));
        when(dishMapper.toDishInfo(dish)).thenReturn(dishInfo);

        List<DishInfo> dishes = dishService.getAllDishes();

        assertEquals(1, dishes.size());
        assertEquals("pasta", dishes.get(0).getName());

        verify(dishRepository).findAll();
        verify(dishMapper).toDishInfo(dish);
    }
}