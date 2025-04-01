package ru.almaz.dailycalorieintake.dto;

import lombok.Data;

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
