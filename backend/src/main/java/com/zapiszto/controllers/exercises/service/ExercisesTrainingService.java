package com.zapiszto.controllers.exercises.service;

import com.zapiszto.controllers.dictionaries.dictEquipment.repository.DictEquipmentRepository;
import com.zapiszto.controllers.dictionaries.dictExercises.repository.DictExercisesRepository;
import com.zapiszto.controllers.dictionaries.dictQuantityType.repository.DictQuantityTypeRepository;
import com.zapiszto.controllers.dictionaries.dictSessionPart.repository.DictSessionPartRepository;
import com.zapiszto.controllers.dictionaries.dictUnits.repository.DictUnitsRepository;
import com.zapiszto.controllers.exercises.dto.ExerciseTrainingDto;
import com.zapiszto.controllers.exercises.dto.NewExerciseTrainingDto;
import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import com.zapiszto.controllers.exercises.repository.ExerciseTrainingRepository;
import com.zapiszto.controllers.exercises.serializer.ExerciseSerializer;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ExercisesTrainingService {

  @Autowired
  ExerciseTrainingRepository exerciseTrainingRepository;

  @Autowired
  DictExercisesRepository dictExercisesRepository;

  @Autowired
  DictQuantityTypeRepository dictQuantityTypeRepository;

  @Autowired
  DictUnitsRepository dictUnitsRepository;



  public void addExercise(NewExerciseTrainingDto newExerciseTrainingDto, Long userId) {
    int trainingId = newExerciseTrainingDto.getTrainingId();
    int orderNumber = exerciseTrainingRepository.getOrderNumber(userId, trainingId);
    ExerciseEntity exerciseEntity = ExerciseSerializer.convert(newExerciseTrainingDto);
    exerciseEntity.setOrderNumber(orderNumber);
    ExerciseEntity save = exerciseTrainingRepository.save(exerciseEntity);
    log.info("add new exercise with id {}, trening_id {}, user {}", save.getId(), save.getTrainingId(), userId);
  }

  public List<ExerciseTrainingDto> getExercises(int trainingId, Long userId) {
    var dictExercises = dictExercisesRepository.getAllForUser(userId);
    var dictQuantityType = dictQuantityTypeRepository.getAllForUser(userId);
    var dictUnits = dictUnitsRepository.getAllForUser(userId);

    List<ExerciseEntity> exercisesByTrainingId = exerciseTrainingRepository.getExercisesByTrainingId(userId, trainingId);


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
      ExerciseEntity exerciseEntity = exerciseTrainingRepository.getByUserIdTrainingIdExerciseId(userId, trainingId, exerciseId);
      exerciseTrainingRepository.delete(exerciseEntity);
      reorder(trainingId);
      log.info("deleted exercise with id {}, training {}, user {}", exerciseId, trainingId, userId);
    } catch (Exception e) {
      log.error("could't delete exercise with id {}, training {}, user {}", exerciseId, trainingId, userId);
    }
  }

  @Transactional
  public void reorder(int trainingId) {
    // Pobierz wszystkie ćwiczenia dla danego trainingId, posortowane według orderNumber
    List<ExerciseEntity> exercises = exerciseTrainingRepository.findByTrainingIdOrderByOrderNumber(trainingId);

    // Przypisz nowe wartości do kolumny orderNumber na podstawie nowego porządku
    for (int i = 0; i < exercises.size(); i++) {
      exercises.get(i).setOrderNumber(i + 1);
    }

    // Zapisz zmiany w bazie danych
    exerciseTrainingRepository.saveAll(exercises);
  }
}
