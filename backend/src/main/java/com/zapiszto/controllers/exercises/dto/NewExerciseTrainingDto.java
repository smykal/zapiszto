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
public class NewExerciseTrainingDto {
  String id;
  int trainingId;
  UUID dictExerciseId;
  int quantity;
  UUID dictQuantityTypeId;
  int volume;
  UUID dictUnitId;
  String notes;
}
