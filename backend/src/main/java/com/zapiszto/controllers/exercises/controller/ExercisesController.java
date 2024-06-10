package com.zapiszto.controllers.exercises.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.exercises.dto.ExerciseDto;
import com.zapiszto.controllers.exercises.dto.NewExerciseDto;
import com.zapiszto.controllers.exercises.service.ExercisesService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')  or hasRole('TRAINER')")
public class ExercisesController implements ControllerCommon {

  @Autowired
  ExercisesService exercisesService;

  @PostMapping("/add_exercise")
  public ResponseEntity<String> addExercise(
      @RequestBody NewExerciseDto newExerciseDto
  ) {
    var userId = extractUserId();
    exercisesService.addExercise(newExerciseDto, userId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/get_exercise/training/{trainingId}")
  public ResponseEntity<List<ExerciseDto>> getExercises(
      @PathVariable("trainingId") int trainingId
  ) {
    var userId = extractUserId();
    try {
      List<ExerciseDto> result = exercisesService.getExercises(trainingId, userId);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/get_exercise/training/{trainingId}/{userId}")
  public ResponseEntity<List<ExerciseDto>> getExercises(
      @PathVariable("trainingId") int trainingId,
      @PathVariable("userId") Long userId
  ) {
    var requestor = extractUserId();
    try {
      List<ExerciseDto> result = exercisesService.getExercises(trainingId, userId);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_exercise/{exerciseId}/{trainingId}")
  public ResponseEntity<String> deleteExercise(
      @PathVariable("exerciseId") int exerciseId,
      @PathVariable("trainingId") int trainingId
  ) {
    var userId = extractUserId();
    try {
      exercisesService.deleteExercise(userId, trainingId, exerciseId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }
}
