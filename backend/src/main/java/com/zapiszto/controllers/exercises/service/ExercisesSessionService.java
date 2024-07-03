package com.zapiszto.controllers.exercises.service;

import com.zapiszto.controllers.exercises.dto.ExerciseSessionDto;
import com.zapiszto.controllers.exercises.dto.NewExerciseSessionDto;
import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import com.zapiszto.controllers.exercises.repository.ExerciseSessionRepository;
import com.zapiszto.controllers.exercises.serializer.ExerciseSerializer;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExercisesSessionService {
  @Autowired
  ExerciseSessionRepository exerciseSessionRepository;

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

  public List<ExerciseSessionDto> getExercises(UUID sessionId){
   return null;
  }
}
