package com.zapiszto.controllers.exercises.dto;

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
public class ExerciseSessionDto {
  String exerciseId;
  UUID sessionId;
  String dictExerciseName;
  String dictCategoryName;
  int quantity;
  String dictQuantityTypeName;
  int volume;
  String dictUnitName;
  String notes;
  int orderNumber;
  Integer restTime;
  String tempo;
  String dictSessionPartName;
  int sets;
}
