package com.zapiszto.controllers.exercises.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.exercises.dto.ExerciseSessionDto;
import com.zapiszto.controllers.exercises.dto.UpdateDictQuantityTypeDto;
import com.zapiszto.controllers.exercises.dto.UpdateDictSessionPartDto;
import com.zapiszto.controllers.exercises.dto.UpdateNotesDto;
import com.zapiszto.controllers.exercises.dto.UpdateQuantityDto;
import com.zapiszto.controllers.exercises.dto.UpdateRestTimeDto;
import com.zapiszto.controllers.exercises.dto.UpdateTempoDto;
import com.zapiszto.controllers.exercises.dto.UpdateVolumeDto;
import com.zapiszto.controllers.exercises.service.ExercisesSessionService;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')  or hasRole('TRAINER')")
public class ExercisesSessionController implements ControllerCommon {
  @Autowired
  ExercisesSessionService exercisesSessionService;


  @GetMapping("/get_exercise/session/{sessionId}")
  public ResponseEntity<List<ExerciseSessionDto>> getExercises(
      @PathVariable("sessionId") UUID sessionId
  ) {
    var userId = extractUserId();
    try {
      var result = exercisesSessionService.getExercises(sessionId, userId);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @PatchMapping("/update_exercise_dict_session_part/{id}")
  public ResponseEntity<String> updateExerciseDictSessionPart(
      @PathVariable UUID id,
      @RequestBody UpdateDictSessionPartDto updateDictSessionPartDto
  ) {
    exercisesSessionService.updateDictSessionPart(id, updateDictSessionPartDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping("/update_exercise_dict_quantity_type/{id}")
  public ResponseEntity<String> updateExerciseDictQuantityType(
      @PathVariable UUID id,
      @RequestBody UpdateDictQuantityTypeDto updateDictQuantityTypeDto
  ) {
    exercisesSessionService.updateDictQuantityType(id, updateDictQuantityTypeDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping("/update_exercise_notes/{id}")
  public ResponseEntity<String> updateExerciseDictQuantityType(
      @PathVariable UUID id,
      @RequestBody UpdateNotesDto updateNotesDto
  ) {
    exercisesSessionService.updateNotes(id, updateNotesDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping("/update_exercise_tempo/{id}")
  public ResponseEntity<String> updateExerciseTempo(
      @PathVariable UUID id,
      @RequestBody UpdateTempoDto updateTempoDto
  ) {
    exercisesSessionService.updateTempo(id, updateTempoDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping("/update_exercise_rest_time/{id}")
  public ResponseEntity<String> updateExerciseRestTime(
      @PathVariable UUID id,
      @RequestBody UpdateRestTimeDto updateRestTimeDto
  ) {
    exercisesSessionService.updateRestTime(id, updateRestTimeDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping("/update_exercise_volume/{id}")
  public ResponseEntity<String> updateExerciseVolume(
      @PathVariable UUID id,
      @RequestBody UpdateVolumeDto updateVolumeDto
  ) {
    exercisesSessionService.updateVolume(id, updateVolumeDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping("/update_exercise_quantity/{id}")
  public ResponseEntity<String> updateExerciseDictQuantityType(
      @PathVariable UUID id,
      @RequestBody UpdateQuantityDto updateQuantityDto
  ) {
    exercisesSessionService.updateQuantity(id, updateQuantityDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
