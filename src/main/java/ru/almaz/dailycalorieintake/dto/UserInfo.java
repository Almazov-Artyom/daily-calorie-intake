package ru.almaz.dailycalorieintake.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ru.almaz.dailycalorieintake.enums.Gender;
import ru.almaz.dailycalorieintake.enums.Purpose;

@Data
public class UserInfo {
    private String username;

    private String email;

    private Integer age;

    private Double weight;

    private Double height;

    private String purpose;

    private String gender;

    private Double dailyNorm;

}
