package ru.almaz.dailycalorieintake.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.almaz.dailycalorieintake.dto.*;
import ru.almaz.dailycalorieintake.entity.Dish;
import ru.almaz.dailycalorieintake.entity.User;
import ru.almaz.dailycalorieintake.entity.UserDish;
import ru.almaz.dailycalorieintake.exception.DishNotFoundException;
import ru.almaz.dailycalorieintake.mapper.DishMapper;
import ru.almaz.dailycalorieintake.repository.DishRepository;
import ru.almaz.dailycalorieintake.repository.UserDishRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDishServiceTest {
    @Mock
    private UserDishRepository userDishRepository;

    @Mock
    private UserService userService;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private UserDishService userDishService;

    @Test
    void addFoodIntake_success() {
        Dish dish1 = new Dish();
        dish1.setId(10L);

        Dish dish2 = new Dish();
        dish2.setId(20L);

        User user = new User();

        AddFoodIntakeRequest request = new AddFoodIntakeRequest();
        request.setDishesId(List.of(10L, 20L));
        request.setDate(LocalDate.of(2025, 4, 1));

        when(userService.getCurrentUser()).thenReturn(user);
        when(dishRepository.findAllById(request.getDishesId())).thenReturn(List.of(dish1, dish2));

        userDishService.addFoodIntake(request);

        verify(userService).getCurrentUser();
        verify(dishRepository).findAllById(request.getDishesId());
        verify(userDishRepository).saveAll(anyList());
    }

    @Test
    void addFoodIntake_dishNotFound() {
        Dish dish1 = new Dish();
        dish1.setId(10L);

        User user = new User();

        AddFoodIntakeRequest request = new AddFoodIntakeRequest();
        request.setDishesId(List.of(10L, 20L));

        when(userService.getCurrentUser()).thenReturn(user);
        when(dishRepository.findAllById(request.getDishesId())).thenReturn(List.of(dish1));

        DishNotFoundException exception = assertThrows(DishNotFoundException.class, () -> userDishService.addFoodIntake(request));
        assertTrue(exception.getMessage().contains("id: [20]"));

        verify(userDishRepository, never()).saveAll(anyList());
    }

    @Test
    void addFoodIntake_dateInDTOIsNull() {
        Dish dish1 = new Dish();
        dish1.setId(10L);

        Dish dish2 = new Dish();
        dish2.setId(20L);

        User user = new User();

        AddFoodIntakeRequest request = new AddFoodIntakeRequest();
        request.setDishesId(List.of(10L, 20L));

        when(userService.getCurrentUser()).thenReturn(user);
        when(dishRepository.findAllById(request.getDishesId())).thenReturn(List.of(dish1, dish2));

        userDishService.addFoodIntake(request);

        verify(userDishRepository).saveAll(anyList());
    }

    @Test
    void getDailyReport() {
        Dish dish1 = new Dish();
        dish1.setCalories(100.0);

        Dish dish2 = new Dish();
        dish2.setCalories(200.0);

        List<Dish> dishes = List.of(dish1, dish2);

        User user = new User();

        UserDish userDish1 = new UserDish();
        userDish1.setUser(user);
        userDish1.setDish(dish1);

        UserDish userDish2 = new UserDish();
        userDish2.setUser(user);
        userDish2.setDish(dish2);

        LocalDate testDate = LocalDate.of(2025, 4, 1);

        DailyReportRequest request = new DailyReportRequest();
        request.setDate(testDate);

        when(userService.getCurrentUser()).thenReturn(user);
        when(userDishRepository.findByUserAndDate(user, testDate)).thenReturn(List.of(userDish1, userDish2));
        when(dishMapper.toDishInfoForDailyReport(dishes)).thenReturn(List.of());

        DailyReportResponse response = userDishService.getDailyReport(request);

        assertNotNull(response);
        assertEquals(300.0, response.getSumAllCalories());
        assertEquals(testDate, response.getDate());
    }

    @Test
    void checkDailyNorm_caloriesLessNorm(){
        Dish dish1 = new Dish();
        dish1.setCalories(500.0);

        Dish dish2 = new Dish();
        dish2.setCalories(700.0);

        User user = new User();
        user.setDailyNorm(1500.0);

        UserDish userDish1 = new UserDish();
        userDish1.setUser(user);
        userDish1.setDish(dish1);

        UserDish userDish2 = new UserDish();
        userDish2.setUser(user);
        userDish2.setDish(dish2);

        LocalDate testDate = LocalDate.of(2025, 4, 1);

        DailyReportRequest request = new DailyReportRequest();
        request.setDate(testDate);

        when(userService.getCurrentUser()).thenReturn(user);
        when(userDishRepository.findByUserAndDate(user,testDate)).thenReturn(List.of(userDish1, userDish2));

        CheckDailyNorm checkDailyNorm = userDishService.checkDailyNorm(request);

        assertEquals(1200.0, checkDailyNorm.getCaloriesPerDay());
        assertEquals(1500.0,checkDailyNorm.getDailyNorm());
        assertEquals("Вы уложились в дневную норму", checkDailyNorm.getMessage());

    }

    @Test
    void checkDailyNorm_caloriesMoreNorm(){
        Dish dish1 = new Dish();
        dish1.setCalories(500.0);

        Dish dish2 = new Dish();
        dish2.setCalories(700.0);

        User user = new User();
        user.setDailyNorm(1000.0);

        UserDish userDish1 = new UserDish();
        userDish1.setUser(user);
        userDish1.setDish(dish1);

        UserDish userDish2 = new UserDish();
        userDish2.setUser(user);
        userDish2.setDish(dish2);

        LocalDate testDate = LocalDate.of(2025, 4, 1);

        DailyReportRequest request = new DailyReportRequest();
        request.setDate(testDate);

        when(userService.getCurrentUser()).thenReturn(user);
        when(userDishRepository.findByUserAndDate(user,testDate)).thenReturn(List.of(userDish1, userDish2));

        CheckDailyNorm checkDailyNorm = userDishService.checkDailyNorm(request);

        assertEquals(1200.0, checkDailyNorm.getCaloriesPerDay());
        assertEquals(1000.0,checkDailyNorm.getDailyNorm());
        assertEquals("Вы не уложились в дневную норму", checkDailyNorm.getMessage());

    }

    @Test
    void getHistoryFoodIntake() {
        Dish dish1 = new Dish();
        dish1.setName("Dish 1");

        Dish dish2 = new Dish();
        dish2.setName("Dish 2");

        LocalDate testDate = LocalDate.of(2025, 4, 1);

        UserDish userDish1 = new UserDish();
        userDish1.setDate(testDate);
        userDish1.setDish(dish1);

        UserDish userDish2 = new UserDish();
        userDish2.setDate(testDate);
        userDish2.setDish(dish2);

        User user = new User();

        HistoryFoodIntake.DishInfoForHistoryFoodIntake dishInfo1 = new HistoryFoodIntake.DishInfoForHistoryFoodIntake();
        dishInfo1.setDishName("Dish 1");
        HistoryFoodIntake.DishInfoForHistoryFoodIntake dishInfo2 = new HistoryFoodIntake.DishInfoForHistoryFoodIntake();
        dishInfo2.setDishName("Dish 2");

        when(userService.getCurrentUser()).thenReturn(user);
        when(userDishRepository.findByUser(user)).thenReturn(List.of(userDish1, userDish2));
        when(dishMapper.toDishInfoForHistoryFoodIntake(dish1)).thenReturn(dishInfo1);
        when(dishMapper.toDishInfoForHistoryFoodIntake(dish2)).thenReturn(dishInfo2);

        HistoryFoodIntake result = userDishService.getHistoryFoodIntake();

        assertNotNull(result);
        assertNotNull(result.getDateAndDishes());
        assertTrue(result.getDateAndDishes().containsKey(testDate));

        List<HistoryFoodIntake.DishInfoForHistoryFoodIntake> dishesForDate
                = result.getDateAndDishes().get(testDate);

        assertEquals(2, dishesForDate.size());
        assertTrue(dishesForDate.stream().anyMatch(d -> d.getDishName().equals("Dish 1")));
        assertTrue(dishesForDate.stream().anyMatch(d -> d.getDishName().equals("Dish 2")));
    }

}