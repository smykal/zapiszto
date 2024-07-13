package com.zapiszto.controllers.exercises.service;

import com.zapiszto.controllers.dictionaries.dictExercises.repository.DictExercisesRepository;
import com.zapiszto.controllers.dictionaries.dictQuantityType.repository.DictQuantityTypeRepository;
import com.zapiszto.controllers.dictionaries.dictSessionPart.repository.DictSessionPartRepository;
import com.zapiszto.controllers.dictionaries.dictUnits.repository.DictUnitsRepository;
import com.zapiszto.controllers.exercises.dto.ExerciseSessionDto;
import com.zapiszto.controllers.exercises.dto.NewExerciseSessionDto;
import com.zapiszto.controllers.exercises.dto.UpdateDictQuantityTypeDto;
import com.zapiszto.controllers.exercises.dto.UpdateDictSessionPartDto;
import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import com.zapiszto.controllers.exercises.repository.ExerciseSessionRepository;
import com.zapiszto.controllers.exercises.serializer.ExerciseSerializer;
import jakarta.persistence.EntityNotFoundException;
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
  ExerciseSerializer exerciseSerializer;

  public void addExercise(NewExerciseSessionDto newExerciseSessionDto){

    ExerciseEntity exerciseEntity = ExerciseSerializer.convert(newExerciseSessionDto);
    exerciseSessionRepository.save(exerciseEntity);
  }

  public void addExercise(UUID sessionId){
    Integer orderId = exerciseSessionRepository.getOrderNumber(sessionId);
    for (int i = 0; i < 10; i++) {
      orderId++;
      ExerciseEntity exerciseEntity = ExerciseSerializer.generateDefaultExerciseSession(sessionId);
      exerciseEntity.setOrderNumber(orderId);
      exerciseSessionRepository.save(exerciseEntity);
    }
  }

  public List<ExerciseSessionDto> getExercises(UUID sessionId, Long userId){
    var dictExercises = dictExercisesRepository.getAllForUser(userId);
    var dictQuantityType = dictQuantityTypeRepository.getAllForUser(userId);
    var dictUnits = dictUnitsRepository.getAllForUser(userId);
    var dictSessionParts = dictSessionPartRepository.getAllForUser(userId);
    List<ExerciseEntity> allBySessionId = exerciseSessionRepository.getAllBySessionId(sessionId);
    return allBySessionId.stream()
        .map(exercise -> ExerciseSerializer.convert(exercise, dictExercises, dictQuantityType, dictUnits, dictSessionParts))
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
      //TODO
    }
  }
}
