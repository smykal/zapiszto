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

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewGoalDto {
  UUID id;
  UUID clientId;
  Integer dictBodyParamId;
  Integer dictBodyTestId;
  Integer dictUnitId;
  String action;
  String value;
  LocalDate goalDate;
}
