package com.zapiszto.controllers.exercises.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictExercises.entity.DictExercisesEntity;
import com.zapiszto.controllers.dictionaries.dictQuantityType.entity.DictQuantityTypeEntity;
import com.zapiszto.controllers.dictionaries.dictUnits.entity.DictUnitsEntity;
import com.zapiszto.controllers.exercises.dto.ExerciseSessionDto;
import com.zapiszto.controllers.exercises.dto.ExerciseTrainingDto;
import com.zapiszto.controllers.exercises.dto.NewExerciseSessionDto;
import com.zapiszto.controllers.exercises.dto.NewExerciseTrainingDto;
import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ExerciseSerializer implements SerializerCommon {

  public static ExerciseEntity convert(NewExerciseTrainingDto newExerciseTrainingDto) {
    return ExerciseEntity.builder()
        .id(UUID.fromString(newExerciseTrainingDto.getId()))
        .trainingId(newExerciseTrainingDto.getTrainingId())
        .dictExerciseId(newExerciseTrainingDto.getDictExerciseId())
        .quantity(newExerciseTrainingDto.getQuantity())
        .dictQuantityTypeId(newExerciseTrainingDto.getDictQuantityTypeId())
        .volume(newExerciseTrainingDto.getVolume())
        .dictUnitId(newExerciseTrainingDto.getDictUnitId())
        .notes(newExerciseTrainingDto.getNotes())
        .build();
  }

  public static ExerciseEntity convert(NewExerciseSessionDto newExerciseSessionDto) {
    return ExerciseEntity.builder()
        .id(UUID.fromString(newExerciseSessionDto.getId()))
        .trainingId(newExerciseSessionDto.getSessionId())
        .dictExerciseId(newExerciseSessionDto.getDictExerciseId())
        .quantity(newExerciseSessionDto.getQuantity())
        .dictQuantityTypeId(newExerciseSessionDto.getDictQuantityTypeId())
        .volume(newExerciseSessionDto.getVolume())
        .dictUnitId(newExerciseSessionDto.getDictUnitId())
        .notes(newExerciseSessionDto.getNotes())
        .build();
  }

  public static List<ExerciseTrainingDto> convert(
      List<ExerciseEntity> exerciseEntity,
      List<DictExercisesEntity> dictExercises,
      List<DictQuantityTypeEntity> dictQuantityType,
      List<DictUnitsEntity> dictUnits
  ) {
    List<ExerciseTrainingDto> exercises = new ArrayList<>();

    for (ExerciseEntity exercise : exerciseEntity) {
      ExerciseTrainingDto exerciseTrainingDto = ExerciseTrainingDto.builder()
          .exerciseId(exercise.getId()
              .toString())
          .trainingId(exercise.getTrainingId())
          .dictExerciseName(getExerciseName(dictExercises, exercise.getDictExerciseId()))
          .quantity(exercise.getQuantity())
          .dictQuantityTypeName(getQuantityTypeName(dictQuantityType, exercise.getDictQuantityTypeId()))
          .volume(exercise.getVolume())
          .dictUnitName(getUnitName(dictUnits, exercise.getDictUnitId()))
          .notes(exercise.getNotes())
          .orderNumber(exercise.getOrderNumber())
          .build();

      exercises.add(exerciseTrainingDto);
    }
    return exercises;
  }

  public static ExerciseSessionDto convert(
      ExerciseEntity exercise,
      List<DictExercisesEntity> dictExercises,
      List<DictQuantityTypeEntity> dictQuantityType,
      List<DictUnitsEntity> dictUnits
  ) {
    return ExerciseSessionDto.builder()
        .exerciseId(exercise.getId()
            .toString())
        .sessionId(exercise.getSessionId())
        .dictExerciseName(getExerciseName(dictExercises, exercise.getDictExerciseId()))
        .quantity(exercise.getQuantity())
        .dictQuantityTypeName(getQuantityTypeName(dictQuantityType, exercise.getDictQuantityTypeId()))
        .volume(exercise.getVolume())
        .dictUnitName(getUnitName(dictUnits, exercise.getDictUnitId()))
        .notes(exercise.getNotes())
        .orderNumber(exercise.getOrderNumber())
        .restTime(exercise.getRestTime())
        .build();
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

  public static ExerciseEntity generateDefaultExerciseSession(UUID sessionId) {

    return ExerciseEntity.builder()
        .id(UUID.randomUUID())
        .sessionId(sessionId)
        .dictExerciseId(DEFAULT_DICT_EXERCISE_ID)
        .quantity(DEFAULT_QUANTITY)
        .dictQuantityTypeId(DEFAULT_DICT_QUANTITY_TYPE)
        .volume(DEFAULT_VOLUME)
        .dictUnitId(DEFAULLT_DICT_UNIT_ID)
        .notes(DEFAULT_NOTES)
        .restTime(DEFAULT_REST_TIME)
        .build();
  }
}
