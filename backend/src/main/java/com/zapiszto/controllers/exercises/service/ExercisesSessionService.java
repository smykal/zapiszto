package com.zapiszto.controllers.exercises.service;

import com.zapiszto.controllers.dictionaries.dictCategory.repository.DictCategoryRepository;
import com.zapiszto.controllers.dictionaries.dictEquipment.repository.DictEquipmentRepository;
import com.zapiszto.controllers.dictionaries.dictExercises.repository.DictExercisesRepository;
import com.zapiszto.controllers.dictionaries.dictQuantityType.repository.DictQuantityTypeRepository;
import com.zapiszto.controllers.dictionaries.dictSessionPart.repository.DictSessionPartRepository;
import com.zapiszto.controllers.dictionaries.dictUnits.repository.DictUnitsRepository;
import com.zapiszto.controllers.exercises.dto.ExerciseSessionDto;
import com.zapiszto.controllers.exercises.dto.NewExerciseSessionDto;
import com.zapiszto.controllers.exercises.dto.UpdateDictQuantityTypeDto;
import com.zapiszto.controllers.exercises.dto.UpdateDictSessionPartDto;
import com.zapiszto.controllers.exercises.dto.UpdateDictUnitDto;
import com.zapiszto.controllers.exercises.dto.UpdateDictEquipmentDto;
import com.zapiszto.controllers.exercises.dto.UpdateEquipmentAttributeDto;
import com.zapiszto.controllers.exercises.dto.UpdateExerciseDto;
import com.zapiszto.controllers.exercises.dto.UpdateNotesDto;
import com.zapiszto.controllers.exercises.dto.UpdateQuantityDto;
import com.zapiszto.controllers.exercises.dto.UpdateRestTimeDto;
import com.zapiszto.controllers.exercises.dto.UpdateSetsDto;
import com.zapiszto.controllers.exercises.dto.UpdateTempoDto;
import com.zapiszto.controllers.exercises.dto.UpdateVolumeDto;
import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import com.zapiszto.controllers.exercises.repository.ExerciseSessionRepository;
import com.zapiszto.controllers.exercises.serializer.ExerciseSerializer;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExercisesSessionService {

  @Autowired
  ExerciseSessionRepository exerciseSessionRepository;

  @Autowired
  DictExercisesRepository dictExercisesRepository;

  @Autowired
  DictQuantityTypeRepository dictQuantityTypeRepository;

  @Autowired
  DictUnitsRepository dictUnitsRepository;

  @Autowired
  DictSessionPartRepository dictSessionPartRepository;

  @Autowired
  DictEquipmentRepository dictEquipmentRepository;

  public void addExercise(NewExerciseSessionDto newExerciseSessionDto) {

    ExerciseEntity exerciseEntity = ExerciseSerializer.convert(newExerciseSessionDto);
    exerciseSessionRepository.save(exerciseEntity);
  }

  public void addExercise(UUID sessionId) {
    Integer orderId = exerciseSessionRepository.getOrderNumber(sessionId);
    for (int i = 0; i < 10; i++) {
      orderId++;
      ExerciseEntity exerciseEntity = ExerciseSerializer.generateDefaultExerciseSession(sessionId);
      exerciseEntity.setOrderNumber(orderId);
      exerciseSessionRepository.save(exerciseEntity);
    }
  }

  public List<ExerciseSessionDto> getExercises(UUID sessionId, Long userId) {
    var dictExercises = dictExercisesRepository.getAllForUser(userId);
    var dictQuantityType = dictQuantityTypeRepository.getAllForUser(userId);
    var dictUnits = dictUnitsRepository.getAllForUser(userId);
    var dictSessionParts = dictSessionPartRepository.getAllForUser(userId);
    var dictEquipment = dictEquipmentRepository.getAllForUser(userId);

    List<ExerciseEntity> allBySessionId = exerciseSessionRepository.getAllBySessionId(sessionId);
    return allBySessionId.stream()
        .map(exercise -> ExerciseSerializer.convert(exercise, dictExercises, dictQuantityType, dictUnits, dictSessionParts, dictEquipment))
        .collect(Collectors.toList());
  }

  public void updateDictSessionPart(UUID id, UpdateDictSessionPartDto updateDictSessionPartDto) {
    Optional<ExerciseEntity> exerciseEntityOptional = exerciseSessionRepository.findById(id);

    if (exerciseEntityOptional.isPresent()) {
      ExerciseEntity exerciseEntity = exerciseEntityOptional.get();
      exerciseEntity.setDictSessionPartId(updateDictSessionPartDto.getId());
      exerciseSessionRepository.save(exerciseEntity);
    } else {
      throw new EntityNotFoundException("Exercise entity not found with ID: " + id);
    }
  }

  public void updateDictQuantityType(UUID id, UpdateDictQuantityTypeDto updateDictQuantityTypeDto) {
    Optional<ExerciseEntity> exerciseEntityOptional = exerciseSessionRepository.findById(id);

    if (exerciseEntityOptional.isPresent()) {
      ExerciseEntity exerciseEntity = exerciseEntityOptional.get();
      exerciseEntity.setDictQuantityTypeId(updateDictQuantityTypeDto.getId());
      exerciseSessionRepository.save(exerciseEntity);
    } else {
      throw new EntityNotFoundException("Exercise entity not found with ID: " + id);
    }
  }

  public void updateNotes(UUID id, UpdateNotesDto updateNotesDto) {
    Optional<ExerciseEntity> exerciseEntityOptional = exerciseSessionRepository.findById(id);

    if (exerciseEntityOptional.isPresent()) {
      ExerciseEntity exerciseEntity = exerciseEntityOptional.get();
      exerciseEntity.setNotes(updateNotesDto.getNotes());
      exerciseSessionRepository.save(exerciseEntity);
    } else {
      throw new EntityNotFoundException("Exercise entity not found with ID: " + id);
    }
  }

  public void updateTempo(UUID id, UpdateTempoDto updateTempoDto) {
    Optional<ExerciseEntity> exerciseEntityOptional = exerciseSessionRepository.findById(id);

    if (exerciseEntityOptional.isPresent()) {
      ExerciseEntity exerciseEntity = exerciseEntityOptional.get();
      exerciseEntity.setTempo(updateTempoDto.getTempo()
          .toUpperCase());
      exerciseSessionRepository.save(exerciseEntity);
    } else {
      throw new EntityNotFoundException("Exercise entity not found with ID: " + id);
    }
  }

  public void updateRestTime(UUID id, UpdateRestTimeDto updateRestTimeDto) {
    Optional<ExerciseEntity> exerciseEntityOptional = exerciseSessionRepository.findById(id);

    if (exerciseEntityOptional.isPresent()) {
      ExerciseEntity exerciseEntity = exerciseEntityOptional.get();
      exerciseEntity.setRestTime(updateRestTimeDto.getRestTime());
      exerciseSessionRepository.save(exerciseEntity);
    } else {
      throw new EntityNotFoundException("Exercise entity not found with ID: " + id);
    }
  }

  public Float updateVolume(UUID id, UpdateVolumeDto updateVolumeDto) {
    Optional<ExerciseEntity> exerciseEntityOptional = exerciseSessionRepository.findById(id);

    if (exerciseEntityOptional.isPresent()) {
      ExerciseEntity exerciseEntity = exerciseEntityOptional.get();
      exerciseEntity.setVolume(updateVolumeDto.getVolume());
      try {
        float equipmentAttribute =Float.parseFloat(exerciseEntity.getEquipmentAttribute());
        float weight = exerciseEntity.getVolume();
        float weightPerSide = (weight - equipmentAttribute) / 2;
        exerciseEntity.setWeightPerSide(weightPerSide);
      }catch (NumberFormatException e) {
        // Log or handle the error appropriately
        System.err.println("Invalid equipment attribute value: " + exerciseEntity.getEquipmentAttribute());
      } finally {
        exerciseSessionRepository.save(exerciseEntity);
        return exerciseEntity.getWeightPerSide();
      }
    } else {
      throw new EntityNotFoundException("Exercise entity not found with ID: " + id);
    }
  }

  public void updateQuantity(UUID id, UpdateQuantityDto updateQuantityDto) {
    Optional<ExerciseEntity> exerciseEntityOptional = exerciseSessionRepository.findById(id);

    if (exerciseEntityOptional.isPresent()) {
      ExerciseEntity exerciseEntity = exerciseEntityOptional.get();
      exerciseEntity.setQuantity(updateQuantityDto.getQuantity());
      exerciseSessionRepository.save(exerciseEntity);
    } else {
      throw new EntityNotFoundException("Exercise entity not found with ID: " + id);
    }
  }

  public void updateExercise(UUID id, UpdateExerciseDto updateExerciseDto) {
    Optional<ExerciseEntity> exerciseEntityOptional = exerciseSessionRepository.findById(id);

    if (exerciseEntityOptional.isPresent()) {
      ExerciseEntity exerciseEntity = exerciseEntityOptional.get();
      exerciseEntity.setDictExerciseId(updateExerciseDto.getId());
      exerciseSessionRepository.save(exerciseEntity);
    } else {
      throw new EntityNotFoundException("Exercise entity not found with ID: " + id);
    }
  }

  public void updateDictUnit(UUID id, UpdateDictUnitDto updateDictUnitDto) {
    Optional<ExerciseEntity> exerciseEntityOptional = exerciseSessionRepository.findById(id);

    if (exerciseEntityOptional.isPresent()) {
      ExerciseEntity exerciseEntity = exerciseEntityOptional.get();
      exerciseEntity.setDictUnitId(updateDictUnitDto.getDictUnitId());
      exerciseSessionRepository.save(exerciseEntity);
    } else {
      throw new EntityNotFoundException("Exercise entity not found with ID: " + id);
    }
  }

  public void updateSets(UUID id, UpdateSetsDto updateSetsDto) {
    Optional<ExerciseEntity> exerciseEntityOptional = exerciseSessionRepository.findById(id);

    if (exerciseEntityOptional.isPresent()) {
      ExerciseEntity exerciseEntity = exerciseEntityOptional.get();
      exerciseEntity.setSets(updateSetsDto.getSets());
      exerciseSessionRepository.save(exerciseEntity);
    } else {
      throw new EntityNotFoundException("Exercise entity not found with ID: " + id);
    }
  }

  public void updateDictEquipment(UUID id, UpdateDictEquipmentDto updateDictEquipmentDto) {
    Optional<ExerciseEntity> exerciseEntityOptional = exerciseSessionRepository.findById(id);

    if (exerciseEntityOptional.isPresent()) {
      ExerciseEntity exerciseEntity = exerciseEntityOptional.get();
      exerciseEntity.setDictEquipmentId(updateDictEquipmentDto.getDictEquipmentId());
      exerciseSessionRepository.save(exerciseEntity);
    } else {
      throw new EntityNotFoundException("Exercise entity not found with ID: " + id);
    }
  }

  public Float updateEquipmentAttribute(UUID id, UpdateEquipmentAttributeDto updateEquipmentAttributeDto) {
    Optional<ExerciseEntity> exerciseEntityOptional = exerciseSessionRepository.findById(id);

    if (exerciseEntityOptional.isPresent()) {
      ExerciseEntity exerciseEntity = exerciseEntityOptional.get();
      exerciseEntity.setEquipmentAttribute(updateEquipmentAttributeDto.getEquipmentAttribute());

      try {
        float equipmentAttribute = Float.parseFloat(updateEquipmentAttributeDto.getEquipmentAttribute());
        float weight = exerciseEntity.getVolume();
        float weightPerSide = (weight - equipmentAttribute) / 2;
        exerciseEntity.setWeightPerSide(weightPerSide);
      } catch (NumberFormatException e) {
        // Log or handle the error appropriately
        System.err.println("Invalid equipment attribute value: " + updateEquipmentAttributeDto.getEquipmentAttribute());
      }
      exerciseSessionRepository.save(exerciseEntity);
      return exerciseEntity.getWeightPerSide();
    } else {
      // Handle the case where the entity was not found, e.g., throw an exception or return null
      throw new EntityNotFoundException("ExerciseEntity not found for ID: " + id);
    }
  }

  @Transactional
  public List<ExerciseSessionDto> delete(UUID sessionId, UUID exerciseId, Long userId) {
    var dictExercises = dictExercisesRepository.getAllForUser(userId);
    var dictQuantityType = dictQuantityTypeRepository.getAllForUser(userId);
    var dictUnits = dictUnitsRepository.getAllForUser(userId);
    var dictSessionParts = dictSessionPartRepository.getAllForUser(userId);
    var dictEquipment = dictEquipmentRepository.getAllForUser(userId);
    exerciseSessionRepository.deleteById(exerciseId);
    List<ExerciseEntity> exerciseEntities = exerciseSessionRepository.getAllBySessionId(sessionId);

    for (int i = 0; i < exerciseEntities.size(); i++) {
      ExerciseEntity exercise = exerciseEntities.get(i);
      exercise.setOrderNumber(i + 1); // Numeracja od 1
    }

    List<ExerciseEntity> entities = exerciseSessionRepository.saveAll(exerciseEntities);
    return entities.stream()
        .map(exercise -> ExerciseSerializer.convert(exercise, dictExercises, dictQuantityType, dictUnits, dictSessionParts, dictEquipment))
        .collect(Collectors.toList());
  }
  @Transactional
  public List<ExerciseSessionDto> updateExerciseOrderNumberUp(UUID sessionId, UUID exerciseId, Long userId) {
    return updateOrderNumber(sessionId, exerciseId, -1, userId);
  }

  @Transactional
  public List<ExerciseSessionDto> updateExerciseOrderNumberDown(UUID sessionId, UUID exerciseId, Long userId) {
    return updateOrderNumber(sessionId, exerciseId, 1, userId);
  }

  private List<ExerciseSessionDto> updateOrderNumber(UUID sessionId, UUID exerciseId, int direction, Long userId) {
    var dictExercises = dictExercisesRepository.getAllForUser(userId);
    var dictQuantityType = dictQuantityTypeRepository.getAllForUser(userId);
    var dictUnits = dictUnitsRepository.getAllForUser(userId);
    var dictSessionParts = dictSessionPartRepository.getAllForUser(userId);
    var dictEquipment = dictEquipmentRepository.getAllForUser(userId);

    List<ExerciseEntity> exerciseEntities = exerciseSessionRepository.getAllBySessionId(sessionId)
        .stream()
        .sorted(Comparator.comparingInt(ExerciseEntity::getOrderNumber))
        .collect(Collectors.toList());

    int index = -1;
    for (int i = 0; i < exerciseEntities.size(); i++) {
      if (exerciseEntities.get(i).getId().equals(exerciseId)) {
        index = i;
        break;
      }
    }

    if (index != -1 && ((direction == -1 && index > 0) || (direction == 1 && index < exerciseEntities.size() - 1))) {
      int newIndex = index + direction;
      ExerciseEntity currentExercise = exerciseEntities.get(index);
      ExerciseEntity targetExercise = exerciseEntities.get(newIndex);

      int currentOrderNumber = currentExercise.getOrderNumber();
      currentExercise.setOrderNumber(targetExercise.getOrderNumber());
      targetExercise.setOrderNumber(currentOrderNumber);

      exerciseSessionRepository.save(currentExercise);
      exerciseSessionRepository.save(targetExercise);
    }

    return exerciseSessionRepository.getAllBySessionId(sessionId).stream()
        .sorted(Comparator.comparingInt(ExerciseEntity::getOrderNumber))
        .map(exercise -> ExerciseSerializer.convert(exercise, dictExercises, dictQuantityType, dictUnits, dictSessionParts, dictEquipment))
        .collect(Collectors.toList());
  }

  @Transactional
  public List<ExerciseSessionDto> addExerciseSession(UUID sessionId, Long userId) {
    var dictExercises = dictExercisesRepository.getAllForUser(userId);
    var dictQuantityType = dictQuantityTypeRepository.getAllForUser(userId);
    var dictUnits = dictUnitsRepository.getAllForUser(userId);
    var dictSessionParts = dictSessionPartRepository.getAllForUser(userId);
    var dictEquipment = dictEquipmentRepository.getAllForUser(userId);

    List<ExerciseEntity> exerciseEntities = exerciseSessionRepository.getAllBySessionId(sessionId)
        .stream()
        .sorted(Comparator.comparingInt(ExerciseEntity::getOrderNumber))
        .collect(Collectors.toList());

    if (exerciseEntities.isEmpty()) {
      return Collections.emptyList();
    }

    ExerciseEntity lastExercise = exerciseEntities.get(exerciseEntities.size() - 1);

    ExerciseEntity newExercise = ExerciseEntity.builder()
        .id(UUID.randomUUID())
        .sessionId(sessionId)
        .trainingId(lastExercise.getTrainingId())
        .dictExerciseId(lastExercise.getDictExerciseId())
        .quantity(lastExercise.getQuantity())
        .dictQuantityTypeId(lastExercise.getDictQuantityTypeId())
        .volume(lastExercise.getVolume())
        .dictUnitId(lastExercise.getDictUnitId())
        .notes(lastExercise.getNotes())
        .orderNumber(lastExercise.getOrderNumber() + 1)
        .restTime(lastExercise.getRestTime())
        .tempo(lastExercise.getTempo())
        .dictSessionPartId(lastExercise.getDictSessionPartId())
        .sets(lastExercise.getSets())
        .dictEquipmentId(lastExercise.getDictEquipmentId())
        .equipmentAttribute(lastExercise.getEquipmentAttribute())
        .weightPerSide(lastExercise.getWeightPerSide())
        .build();

    exerciseSessionRepository.save(newExercise);

    List<ExerciseEntity> updatedExerciseEntities = exerciseSessionRepository.getAllBySessionId(sessionId)
        .stream()
        .sorted(Comparator.comparingInt(ExerciseEntity::getOrderNumber))
        .collect(Collectors.toList());

    return updatedExerciseEntities.stream()
        .map(exercise -> ExerciseSerializer.convert(exercise, dictExercises, dictQuantityType, dictUnits, dictSessionParts, dictEquipment))
        .collect(Collectors.toList());
  }
}

