package ru.almaz.dailycalorieintake.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.almaz.dailycalorieintake.dto.AddFoodIntakeRequest;
import ru.almaz.dailycalorieintake.dto.CheckDailyNorm;
import ru.almaz.dailycalorieintake.dto.DailyReportRequest;
import ru.almaz.dailycalorieintake.dto.DailyReportResponse;
import ru.almaz.dailycalorieintake.entity.UserDish;
import ru.almaz.dailycalorieintake.service.UserDishService;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-dish")
public class UserDishController {
    private final UserDishService userDishService;

    @PostMapping("/add-food-intake")
    public void addFoodIntake(@RequestBody @Valid AddFoodIntakeRequest request) {
        userDishService.addFoodIntake(request);
    }

    @GetMapping("/daily-report")
    public DailyReportResponse dailyReport(@RequestBody(required = false) DailyReportRequest request){
        if(request == null){
            request = new DailyReportRequest();
        }
        return userDishService.getDailyReport(request);
    }

    @GetMapping("/check-daily-norm")
    public CheckDailyNorm checkDailyNorm(@RequestBody(required = false) DailyReportRequest request) {
        if(request == null){
            request = new DailyReportRequest();
        }
        return userDishService.checkDailyNorm(request);
    }

}
