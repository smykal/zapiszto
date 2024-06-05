package com.zapiszto.controllers.exercises.serializer;

import com.zapiszto.controllers.dictionaries.dictExercises.entity.DictExercisesEntity;
import com.zapiszto.controllers.dictionaries.dictQuantityType.entity.DictQuantityTypeEntity;
import com.zapiszto.controllers.dictionaries.dictUnits.entity.DictUnitsEntity;
import com.zapiszto.controllers.exercises.dto.ExerciseDto;
import com.zapiszto.controllers.exercises.dto.NewExerciseDto;
import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

  public static List<ExerciseDto> convert(
      List<ExerciseEntity> exerciseEntity,
      List<DictExercisesEntity> dictExercises,
      List<DictQuantityTypeEntity> dictQuantityType,
      List<DictUnitsEntity> dictUnits
  ) {
    List<ExerciseDto> exercises = new ArrayList<>();

    for (ExerciseEntity exercise : exerciseEntity) {
      ExerciseDto exerciseDto = ExerciseDto.builder()
          .exerciseId(exercise.getId())
          .trainingId(exercise.getTrainingId())
          .dictExerciseName(getExerciseName(dictExercises, exercise.getDictExerciseId()))
          .quantity(exercise.getQuantity())
          .dictQuantityTypeName(getQuantityTypeName(dictQuantityType, exercise.getDictQuantityTypeId()))
          .volume(exercise.getVolume())
          .dictUnitName(getUnitName(dictUnits, exercise.getDictUnitId()))
          .notes(exercise.getNotes())
          .orderNumber(exercise.getOrderNumber())
          .build();

      exercises.add(exerciseDto);
    }
    return exercises;
  }

  private static String getExerciseName(List<DictExercisesEntity> dictExercises, int dictExerciseId) {
    Optional<DictExercisesEntity> exerciseOptional = dictExercises.stream()
        .filter(exercise -> exercise.getId() == dictExerciseId)
        .findFirst();

    if (exerciseOptional.isPresent()) {
      DictExercisesEntity exercise = exerciseOptional.get();
      if (exercise.getDictExercisesBasicEntity() != null) {
        return exercise.getDictExercisesBasicEntity()
            .getName();
      } else if (exercise.getDictExercisesPerUserEntity() != null) {
        return exercise.getDictExercisesPerUserEntity()
            .getName();
      }
    }

    return null;
  }

  private static String getQuantityTypeName(List<DictQuantityTypeEntity> dictQuantityType, int dictQuantityTypeId) {
    Optional<DictQuantityTypeEntity> quantityTypeEntity = dictQuantityType.stream()
        .filter(quantityType -> quantityType.getId() == dictQuantityTypeId)
        .findFirst();

    if (quantityTypeEntity.isPresent()) {
      DictQuantityTypeEntity quantityType = quantityTypeEntity.get();
      if (quantityType.getDictQuantityTypeBasicEntity() != null) {
        return quantityType.getDictQuantityTypeBasicEntity()
            .getName();
      } else if (quantityType.getDictQuantityTypePerUserEntity() != null) {
        return quantityType.getDictQuantityTypePerUserEntity()
            .getName();
      }
    }

    return null;
  }

  private static String getUnitName(List<DictUnitsEntity> dictUnits, int dictUnitId) {
    Optional<DictUnitsEntity> unitEntity = dictUnits.stream()
        .filter(dictUnit -> dictUnit.getId() == dictUnitId)
        .findFirst();

    if (unitEntity.isPresent()) {
      DictUnitsEntity dictUnitsEntity = unitEntity.get();
      if (dictUnitsEntity.getDictUnitsBasicEntity() != null) {
        return dictUnitsEntity.getDictUnitsBasicEntity()
            .getName();
      } else if (dictUnitsEntity.getDictUnitsPerUserEntity() != null) {
        return dictUnitsEntity.getDictUnitsPerUserEntity()
            .getName();
      }
    }

    return null;
  }
}
