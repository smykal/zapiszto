package com.zapiszto.controllers.goals.dto;

import java.time.LocalDate;
import java.util.UUID;


public record NewGoalDto(
    UUID id,
    UUID clientId,
    Integer dictBodyParamId,
    UUID dictBodyTestId,
    UUID dictUnitId,
    String action,
    String value,
    LocalDate goalDate) {}
