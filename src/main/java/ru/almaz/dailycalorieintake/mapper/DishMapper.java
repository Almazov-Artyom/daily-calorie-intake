package ru.almaz.dailycalorieintake.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.almaz.dailycalorieintake.dto.AddDishRequest;
import ru.almaz.dailycalorieintake.dto.DailyReportResponse;
import ru.almaz.dailycalorieintake.dto.DishInfo;
import ru.almaz.dailycalorieintake.dto.HistoryFoodIntake;
import ru.almaz.dailycalorieintake.entity.Dish;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishMapper {
    Dish toDish(AddDishRequest addDishRequest);

    DishInfo toDishInfo(Dish dish);

    @Mapping(source = "name", target = "dishName")
    DailyReportResponse.DishInfoForDailyReport toDishInfoForDailyReport(Dish dish);

    List<DailyReportResponse.DishInfoForDailyReport> toDishInfoForDailyReport(List<Dish> dish);

    @Mapping(source = "name", target = "dishName")
    HistoryFoodIntake.DishInfoForHistoryFoodIntake toDishInfoForHistoryFoodIntake(Dish dish);
}
