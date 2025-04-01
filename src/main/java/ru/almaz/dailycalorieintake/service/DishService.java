package ru.almaz.dailycalorieintake.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.dailycalorieintake.dto.AddDishRequest;
import ru.almaz.dailycalorieintake.dto.DishInfo;
import ru.almaz.dailycalorieintake.entity.Dish;
import ru.almaz.dailycalorieintake.exception.DishAlreadyExist;
import ru.almaz.dailycalorieintake.mapper.DishMapper;
import ru.almaz.dailycalorieintake.repository.DishRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;

    private final DishMapper dishMapper;

    @Transactional
    public void addDish(AddDishRequest addDishRequest) {
        if(dishRepository.existsByName(addDishRequest.getName())) {
            throw new DishAlreadyExist("Такое блюдо уже есть");
        }
        Dish dish = dishMapper.toDish(addDishRequest);
        dishRepository.save(dish);
    }

    @Transactional(readOnly = true)
    public List<DishInfo> getAllDishes() {
        return dishRepository.findAll().stream()
                .map(dishMapper::toDishInfo)
                .collect(Collectors.toList());
    }

}
