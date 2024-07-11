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
public class NewExerciseSessionDto {
  String id;
  int sessionId;
  UUID dictExerciseId;
  int quantity;
  int dictQuantityTypeId;
  int volume;
  int dictUnitId;
  String notes;
  Integer restTime;
  String tempo;
  UUID dictSessionPartId;
}
