package com.zapiszto.controllers.dictExercises.controller;

import com.zapiszto.controllers.ControllerCommon;
import com.zapiszto.controllers.dictExercises.dto.DictExercisesDto;
import com.zapiszto.controllers.dictExercises.dto.NewDictExerciseDto;
import com.zapiszto.controllers.dictExercises.service.DictExerciseService;
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
public class DictExercisesController implements ControllerCommon {

  @Autowired
  DictExerciseService dictExerciseService;

  @PostMapping("/add_exercise_per_user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> addExercisePerUser(
      @RequestBody NewDictExerciseDto newDictExerciseDto
  ) {
    var userId = extractUserId();
    dictExerciseService.addDictExercise(newDictExerciseDto, userId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/add_exercise_basic")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> addExerciseBasic(
      @RequestBody NewDictExerciseDto newDictExerciseDto
  ) {
    var userId = extractUserId();
    var role = extractUserRole();
    dictExerciseService.addDictExercise(newDictExerciseDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/get_exercise_basic")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<DictExercisesDto>> getExerciseBasic(
  ) {
    var userId = extractUserId();
    try {
      var result = dictExerciseService.getDictExercises(userId);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

  }
  @GetMapping("/get_exercises_per_user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<DictExercisesDto>> getExercisesPerUser(
  ) {
    var userId = extractUserId();
    try {
      var result = dictExerciseService.getDictExercises(userId);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }
  @DeleteMapping("/delete_exercise_per_user/{itemToDelete}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> deleteExercisePerUser(
      @PathVariable("itemToDelete") int itemToDelete
  ) {
    var userId = extractUserId();
    try {
      var result = dictExerciseService.deleteDictExercise(userId, itemToDelete);
      return new ResponseEntity<>( HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }
}
