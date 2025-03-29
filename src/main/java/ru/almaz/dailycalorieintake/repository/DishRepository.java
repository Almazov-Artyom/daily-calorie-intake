package ru.almaz.dailycalorieintake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.almaz.dailycalorieintake.entity.Dish;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    Dish findById(long id);

    boolean existsByName(String name);

    @Query("SELECT d FROM Dish d")
    List<Dish> findAllDishes();
}
