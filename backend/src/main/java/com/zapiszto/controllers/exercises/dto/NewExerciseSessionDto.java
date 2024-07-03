package com.zapiszto.controllers.exercises.dto;

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
public class NewExerciseSessionDto {
  String id;
  int sessionId;
  int dictExerciseId;
  int quantity;
  int dictQuantityTypeId;
  int volume;
  int dictUnitId;
  String notes;
}
