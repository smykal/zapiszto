package com.zapiszto.controllers.exercises.serializer;

import com.zapiszto.controllers.exercises.dto.NewExerciseDto;
import com.zapiszto.controllers.exercises.entity.ExerciseEntity;

public class ExerciseSerializer {

  public static ExerciseEntity convert(NewExerciseDto newExerciseDto) {
    return ExerciseEntity.builder()
        .trainingId(newExerciseDto.getTrainingId())
        .dictExerciseId(newExerciseDto.getDictExerciseId())
        .quantity(newExerciseDto.getQuantity())
        .dictQuantityTypeId(newExerciseDto.getDictQuantityTypeId())
        .volume(newExerciseDto.getVolume())
        .dictUnitId(newExerciseDto.getDictUnitId())
        .notes(newExerciseDto.getNotes())
        .build();
  }
}
