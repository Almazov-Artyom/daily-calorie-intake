package ru.almaz.dailycalorieintake.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyReportRequest {
    private LocalDate date;
}
