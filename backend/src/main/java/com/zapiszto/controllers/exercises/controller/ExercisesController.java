package com.zapiszto.controllers.exercises.controller;

import com.zapiszto.controllers.ControllerCommon;
import com.zapiszto.controllers.exercises.dto.ExerciseDto;
import com.zapiszto.controllers.exercises.dto.NewExerciseDto;
import com.zapiszto.controllers.exercises.service.ExercisesService;
import com.zapiszto.controllers.trainings.dto.TrainingDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class ExercisesController implements ControllerCommon {

  @Autowired
  ExercisesService exercisesService;

  @PostMapping("/add_exercise")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> addExercise(
      @RequestBody NewExerciseDto newExerciseDto
      ) {
    var userId = extractUserId();
    exercisesService.addExercise(newExerciseDto, userId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/get_exercise/training/{trainingId}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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
}
