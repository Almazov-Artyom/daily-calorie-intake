package ru.almaz.dailycalorieintake.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.almaz.dailycalorieintake.dto.*;
import ru.almaz.dailycalorieintake.service.UserDishService;

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
    public DailyReportResponse dailyReport(@RequestBody(required = false) DailyReportRequest request) {
        if (request == null) {
            request = new DailyReportRequest();
        }
        return userDishService.getDailyReport(request);
    }

    @GetMapping("/check-daily-norm")
    public CheckDailyNorm checkDailyNorm(@RequestBody(required = false) DailyReportRequest request) {
        if (request == null) {
            request = new DailyReportRequest();
        }
        return userDishService.checkDailyNorm(request);
    }

    @GetMapping("/history-food-intake")
    public HistoryFoodIntake getHistoryFoodIntake() {
        return userDishService.getHistoryFoodIntake();
    }
}
