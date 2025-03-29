package ru.almaz.dailycalorieintake.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.almaz.dailycalorieintake.dto.UpdateAgeRequest;
import ru.almaz.dailycalorieintake.dto.UpdateHeightRequest;
import ru.almaz.dailycalorieintake.dto.UpdateWeightRequest;
import ru.almaz.dailycalorieintake.dto.UserInfo;
import ru.almaz.dailycalorieintake.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/update-height")
    public UserInfo updateEmail(@RequestBody @Valid UpdateHeightRequest request) {
        return userService.updateHeight(request);
    }

    @PostMapping("/update-weight")
    public UserInfo updateEmail(@RequestBody @Valid UpdateWeightRequest request) {
        return userService.updateWeight(request);
    }

    @PostMapping("/update-age")
    public UserInfo updateEmail(@RequestBody @Valid UpdateAgeRequest request) {
        return userService.updateAge(request);
    }

}
