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
public class ExerciseDto {
  int exerciseId;
  int trainingId;
  String dictExerciseName;
  int quantity;
  String dictQuantityTypeName;
  int volume;
  String dictUnitName;
  String notes;
  int orderNumber;
}
