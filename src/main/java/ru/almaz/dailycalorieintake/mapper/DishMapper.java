package ru.almaz.dailycalorieintake.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.almaz.dailycalorieintake.dto.AddDishRequest;
import ru.almaz.dailycalorieintake.dto.DishInfo;
import ru.almaz.dailycalorieintake.entity.Dish;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishMapper {
    Dish toDish(AddDishRequest addDishRequest);

    DishInfo toDishInfo(Dish dish);
}
