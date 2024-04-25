package com.bezkoder.spring.security.postgresql.controllers.dictExercises.controller;

import com.bezkoder.spring.security.postgresql.controllers.ControllerCommon;
import com.bezkoder.spring.security.postgresql.controllers.dictExercises.dto.NewDictExerciseDto;
import com.bezkoder.spring.security.postgresql.controllers.dictExercises.service.DictExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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

}