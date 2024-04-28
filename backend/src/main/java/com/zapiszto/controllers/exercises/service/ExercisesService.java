package com.zapiszto.controllers.exercises.service;

import com.zapiszto.controllers.exercises.dto.NewExerciseDto;
import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import com.zapiszto.controllers.exercises.repository.ExerciseRepository;
import com.zapiszto.controllers.exercises.serializer.ExerciseSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExercisesService {

  @Autowired
  ExerciseRepository exerciseRepository;

  public void addExercise(NewExerciseDto newExerciseDto, Long userId) {
    ExerciseEntity exerciseEntity = ExerciseSerializer.convert(newExerciseDto);
    ExerciseEntity save = exerciseRepository.save(exerciseEntity);
    log.info("add new exercise with id {}, trening_id {}, user {}", save.getId(), save.getTrainingId(), userId);
  }
}
