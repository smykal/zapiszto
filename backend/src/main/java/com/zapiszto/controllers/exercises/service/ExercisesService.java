package com.zapiszto.controllers.exercises.service;

import com.zapiszto.controllers.dictExercises.repository.DictExercisesRepository;
import com.zapiszto.controllers.dictQuantityType.repository.DictQuantityTypeRepository;
import com.zapiszto.controllers.dictUnits.repository.DictUnitsRepository;
import com.zapiszto.controllers.exercises.dto.ExerciseDto;
import com.zapiszto.controllers.exercises.dto.NewExerciseDto;
import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import com.zapiszto.controllers.exercises.repository.ExerciseRepository;
import com.zapiszto.controllers.exercises.serializer.ExerciseSerializer;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ExercisesService {

  @Autowired
  ExerciseRepository exerciseRepository;

  @Autowired
  DictExercisesRepository dictExercisesRepository;

  @Autowired
  DictQuantityTypeRepository dictQuantityTypeRepository;

  @Autowired
  DictUnitsRepository dictUnitsRepository;

  public void addExercise(NewExerciseDto newExerciseDto, Long userId) {
    int trainingId = newExerciseDto.getTrainingId();
    int orderNumber = exerciseRepository.getOrderNumber(userId, trainingId);
    ExerciseEntity exerciseEntity = ExerciseSerializer.convert(newExerciseDto);
    exerciseEntity.setOrderNumber(orderNumber);
    ExerciseEntity save = exerciseRepository.save(exerciseEntity);
    log.info("add new exercise with id {}, trening_id {}, user {}", save.getId(), save.getTrainingId(), userId);
  }

  public List<ExerciseDto> getExercises(int trainingId, Long userId) {
    var dictExercises = dictExercisesRepository.getAllForUser(userId);
    var dictQuantityType = dictQuantityTypeRepository.getAllForUser(userId);
    var dictUnits = dictUnitsRepository.getAllForUser(userId);

    List<ExerciseEntity> exercisesByTrainingId = exerciseRepository.getExercisesByTrainingId(userId, trainingId);


    return ExerciseSerializer.convert(
        exercisesByTrainingId,
        dictExercises,
        dictQuantityType,
        dictUnits
    );
  }

  @Transactional
  public void deleteExercise(Long userId, int trainingId, int exerciseId) {
    try {
      ExerciseEntity exerciseEntity = exerciseRepository.getByUserIdTrainingIdExerciseId(userId, trainingId, exerciseId);
      exerciseRepository.delete(exerciseEntity);
      reorder(trainingId);
      log.info("deleted exercise with id {}, training {}, user {}", exerciseId, trainingId, userId);
    } catch (Exception e) {
      log.error("could't delete exercise with id {}, training {}, user {}", exerciseId, trainingId, userId);
    }
  }

  @Transactional
  public void reorder(int trainingId) {
    // Pobierz wszystkie ćwiczenia dla danego trainingId, posortowane według orderNumber
    List<ExerciseEntity> exercises = exerciseRepository.findByTrainingIdOrderByOrderNumber(trainingId);

    // Przypisz nowe wartości do kolumny orderNumber na podstawie nowego porządku
    for (int i = 0; i < exercises.size(); i++) {
      exercises.get(i).setOrderNumber(i + 1);
    }

    // Zapisz zmiany w bazie danych
    exerciseRepository.saveAll(exercises);
  }
}
