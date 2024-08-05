package com.zapiszto.controllers.goals.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

public record GoalDto (
  UUID id,
  UUID clientId,
  String goalType,
  String dictBodyParam,
  String dictBodyTestType,
  String dictBodyTest,
  String dictUnitType,
  String dictUnit,
  String action,
  String value,
  LocalDate goalDate,
  ZonedDateTime insertDate){
}
