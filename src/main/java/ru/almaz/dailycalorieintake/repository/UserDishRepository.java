package ru.almaz.dailycalorieintake.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.almaz.dailycalorieintake.entity.User;
import ru.almaz.dailycalorieintake.entity.UserDish;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserDishRepository extends JpaRepository<UserDish, Long> {
    @EntityGraph(attributePaths = {"dish", "user"})
    List<UserDish> findByUserAndDate(User user, LocalDate date);

    @EntityGraph(attributePaths = {"dish", "user"})
    List<UserDish> findByUser(User user);
}
