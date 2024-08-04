package com.zapiszto.controllers.exercises.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictEquipment.entity.DictEquipmentEntity;
import com.zapiszto.controllers.dictionaries.dictExercises.entity.DictExercisesEntity;
import com.zapiszto.controllers.dictionaries.dictQuantityType.entity.DictQuantityTypeEntity;
import com.zapiszto.controllers.dictionaries.dictSessionPart.entity.DictSessionPartEntity;
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
      List<DictUnitsEntity> dictUnits,
      List<DictSessionPartEntity> dictSessionParts,
      List<DictEquipmentEntity> dictEquipments
  ) {
    return ExerciseSessionDto.builder()
        .exerciseId(exercise.getId()
            .toString())
        .sessionId(exercise.getSessionId())
        .dictExerciseName(getExerciseName(dictExercises, exercise.getDictExerciseId()))
        .dictCategoryName(getDictCategoryName(dictExercises, exercise.getDictExerciseId()))
        .quantity(exercise.getQuantity())
        .dictQuantityTypeName(getQuantityTypeName(dictQuantityType, exercise.getDictQuantityTypeId()))
        .volume(exercise.getVolume())
        .dictUnitName(getUnitName(dictUnits, exercise.getDictUnitId()))
        .notes(exercise.getNotes())
        .orderNumber(exercise.getOrderNumber())
        .restTime(exercise.getRestTime())
        .tempo(exercise.getTempo())
        .dictSessionPartName(getSessionPartName(dictSessionParts, exercise.getDictSessionPartId()))
        .sets(exercise.getSets())
        .equipmentName(getEquipmentName(dictEquipments, exercise.getDictEquipmentId()))
        .equipmentAttribute(exercise.getEquipmentAttribute())
        .weightPerSide(exercise.getWeightPerSide())
        .duration(exercise.getDuration())
        .build();
  }

  public static String getDictCategoryName(List<DictExercisesEntity> dictExercises, UUID dictExerciseId) {
    Optional<DictExercisesEntity> exerciseOptional = dictExercises.stream()
        .filter(exercise -> exercise.getId()
            .equals(dictExerciseId))
        .findFirst();

    if (exerciseOptional.isPresent()) {
      DictExercisesEntity exercise = exerciseOptional.get();
      if (exercise.getDictExercisesBasicEntity() != null) {
        return exercise.getDictExercisesBasicEntity()
            .getDictCategoryEntity()
            .getDictCategoryBasicEntity()
            .getName();
      } else if (exercise.getDictExercisesPerUserEntity() != null) {
        if (exercise.getDictExercisesPerUserEntity()
            .getDictCategoryEntity()
            .getDictCategoryBasicEntity() != null) {
          return exercise.getDictExercisesPerUserEntity()
              .getDictCategoryEntity()
              .getDictCategoryBasicEntity()
              .getName();
        } else {
          return exercise.getDictExercisesPerUserEntity()
              .getDictCategoryEntity()
              .getDictCategoryPerUserEntity()
              .getName();
        }
      }
    }

    return null;
  }

  public static String getSessionPartName(List<DictSessionPartEntity> dictSessionPart, UUID dictSessionPartId) {
    Optional<DictSessionPartEntity> sessionPartOptional = dictSessionPart.stream()
        .filter(sessionPart -> sessionPart.getId()
            .equals(dictSessionPartId))
        .findFirst();

    if (sessionPartOptional.isPresent()) {
      DictSessionPartEntity exercise = sessionPartOptional.get();
      if (exercise.getDictSessionPartBasicEntity() != null) {
        return exercise.getDictSessionPartBasicEntity()
            .getName();
      } else if (exercise.getDictSessionPartPerUserEntity() != null) {
        return exercise.getDictSessionPartPerUserEntity()
            .getName();
      }
    }
    return null;
  }


  public static String getExerciseName(List<DictExercisesEntity> dictExercises, UUID dictExerciseId) {
    Optional<DictExercisesEntity> exerciseOptional = dictExercises.stream()
        .filter(exercise -> exercise.getId()
            .equals(dictExerciseId))
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

  public static String getQuantityTypeName(List<DictQuantityTypeEntity> dictQuantityType, UUID dictQuantityTypeId) {
    Optional<DictQuantityTypeEntity> quantityTypeEntity = dictQuantityType.stream()
        .filter(quantityType -> quantityType.getId()
            .equals(dictQuantityTypeId))
        .findFirst();

    if (quantityTypeEntity.isPresent()) {
      DictQuantityTypeEntity quantityType = quantityTypeEntity.get();
      if (quantityType.getDictQuantityTypeBasicEntity() != null) {
        String shortcut = quantityType.getDictQuantityTypeBasicEntity()
            .getShortcut().get("en");
        if (shortcut != null && !shortcut.isEmpty()) {
          return shortcut;
        }
        return quantityType.getDictQuantityTypeBasicEntity()
            .getName().get("en");
      } else if (quantityType.getDictQuantityTypePerUserEntity() != null) {
        String shortcut = quantityType.getDictQuantityTypePerUserEntity()
            .getShortcut().get("en");
        if (shortcut != null && !shortcut.isEmpty()) {
          return shortcut;
        }
        return quantityType.getDictQuantityTypePerUserEntity()
            .getName().get("en");
      }
    }

    return null;
  }


  public static String getUnitName(List<DictUnitsEntity> dictUnits, UUID dictUnitId) {
    Optional<DictUnitsEntity> unitEntity = dictUnits.stream()
        .filter(dictUnit -> dictUnit.getId().equals(dictUnitId))
        .findFirst();

    if (unitEntity.isPresent()) {
      DictUnitsEntity dictUnitsEntity = unitEntity.get();
      if (dictUnitsEntity.getDictUnitsBasicEntity() != null) {
        String shortcut = dictUnitsEntity.getDictUnitsBasicEntity()
            .getShortcut();
        if (shortcut != null && !shortcut.isEmpty()) {
          return shortcut;
        }
        return dictUnitsEntity.getDictUnitsBasicEntity()
            .getName();
      } else if (dictUnitsEntity.getDictUnitsPerUserEntity() != null) {
        String shortcut = dictUnitsEntity.getDictUnitsPerUserEntity()
            .getShortcut();
        if (shortcut != null && !shortcut.isEmpty()) {
          return shortcut;
        }
        return dictUnitsEntity.getDictUnitsPerUserEntity()
            .getName();
      }
    }
    return null;
  }

  private static String getEquipmentName(List<DictEquipmentEntity> dictEquipment, UUID dictEquipmentId) {
    Optional<DictEquipmentEntity> exerciseOptional = dictEquipment.stream()
        .filter(exercise -> exercise.getId()
            .equals(dictEquipmentId))
        .findFirst();

    if (exerciseOptional.isPresent()) {
      DictEquipmentEntity exercise = exerciseOptional.get();
      if (exercise.getDictEquipmentBasicEntity() != null) {
        return exercise.getDictEquipmentBasicEntity()
            .getName();
      } else if (exercise.getDictEquipmentPerUserEntity() != null) {
        return exercise.getDictEquipmentPerUserEntity()
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
        .dictSessionPartId(DEFAULT_DICT_SESSION_PART_ID)
        .quantity(DEFAULT_QUANTITY)
        .dictQuantityTypeId(DEFAULT_DICT_QUANTITY_TYPE)
        .volume(DEFAULT_VOLUME)
        .dictUnitId(DEFAULLT_DICT_UNIT_ID)
        .notes(DEFAULT_NOTES)
        .restTime(DEFAULT_REST_TIME)
        .tempo(DEFAULT_TEMPO)
        .sets(DEFAULT_SETS)
        .build();
  }
}
