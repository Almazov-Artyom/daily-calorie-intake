package ru.almaz.dailycalorieintake.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.almaz.dailycalorieintake.dto.*;
import ru.almaz.dailycalorieintake.entity.Dish;
import ru.almaz.dailycalorieintake.entity.User;
import ru.almaz.dailycalorieintake.entity.UserDish;
import ru.almaz.dailycalorieintake.exception.DishNotFoundException;
import ru.almaz.dailycalorieintake.mapper.DishMapper;
import ru.almaz.dailycalorieintake.repository.DishRepository;
import ru.almaz.dailycalorieintake.repository.UserDishRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDishService {
    private final UserDishRepository userDishRepository;

    private final UserService userService;

    private final DishRepository dishRepository;

    private final DishMapper dishMapper;

    Double getSumCalories(List<Dish> dishes) {
        return dishes.stream()
                .map(Dish::getCalories)
                .reduce(0.0, Double::sum);
    }

    @Transactional(readOnly = true)
    public List<Dish> getDishesFromUserDishes(DailyReportRequest request, User user) {

        List<UserDish> userDishes = userDishRepository.findByUserAndDate(user,
                request.getDate() != null ? request.getDate() : LocalDate.now());

        return userDishes.stream()
                .map(UserDish::getDish)
                .toList();
    }

    @Transactional
    public void addFoodIntake(AddFoodIntakeRequest request) {
        User user = userService.getCurrentUser();

        List<Dish> dishes = dishRepository.findAllById(request.getDishesId());

        Set<Long> foundIds = dishes.stream().map(Dish::getId).collect(Collectors.toSet());

        List<Long> notFoundIds = request.getDishesId()
                .stream()
                .filter(id -> !foundIds.contains(id))
                .toList();
        if (!notFoundIds.isEmpty()) {
            throw new DishNotFoundException("Блюдо с id: " + notFoundIds + " не найдено");

        }

        List<UserDish> userDishes = dishes.stream()
                .map(dish -> UserDish.builder()
                        .user(user)
                        .dish(dish)
                        .date(request.getDate() != null ? request.getDate() : LocalDate.now())
                        .build()
                )
                .toList();

        userDishRepository.saveAll(userDishes);
    }

    @Transactional(readOnly = true)
    public DailyReportResponse getDailyReport(DailyReportRequest request) {
        User user = userService.getCurrentUser();

        List<Dish> dishes = getDishesFromUserDishes(request, user);

        Double sumCalories = getSumCalories(dishes);

        return DailyReportResponse.builder()
                .sumAllCalories(sumCalories)
                .dishes(dishMapper.toDishInfoForDailyReport(dishes))
                .date(request.getDate() != null ? request.getDate() : LocalDate.now())
                .build();
    }

    @Transactional(readOnly = true)
    public CheckDailyNorm checkDailyNorm(DailyReportRequest request) {
        User user = userService.getCurrentUser();

        List<Dish> dishes = getDishesFromUserDishes(request, user);

        Double sumCalories = getSumCalories(dishes);

        Double dailyNorm = user.getDailyNorm();

        String message = "";

        if (sumCalories <= dailyNorm) {
            message = "Вы уложились в дневную норму";
        } else
            message = "Вы не уложились в дневную норму";

        return CheckDailyNorm.builder()
                .message(message)
                .dailyNorm(dailyNorm)
                .caloriesPerDay(sumCalories)
                .build();
    }

    @Transactional(readOnly = true)
    public HistoryFoodIntake getHistoryFoodIntake() {
        User user = userService.getCurrentUser();

        List<UserDish> userDishes = userDishRepository.findByUser(user);

        Map<LocalDate, List<HistoryFoodIntake.DishInfoForHistoryFoodIntake>> dateAndDishes = userDishes.stream()
                .collect(Collectors.groupingBy(
                                UserDish::getDate,
                                Collectors.mapping(
                                        userDish -> dishMapper.toDishInfoForHistoryFoodIntake(userDish.getDish()),
                                        Collectors.toList()
                                )
                        )
                );

        return HistoryFoodIntake.builder()
                .dateAndDishes(dateAndDishes)
                .build();
    }

}
