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
    ExerciseEntity exerciseEntity = ExerciseSerializer.convert(newExerciseDto);
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
}
