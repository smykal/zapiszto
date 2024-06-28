package com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.controller;


import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dto.DictMicrocyclePhaseDto;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dto.NewDictMicrocyclePhaseDto;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.service.DictMicrocyclePhaseService;
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
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class DictMicrocyclePhaseController implements ControllerCommon {
  @Autowired
  DictMicrocyclePhaseService dictMicrocyclePhaseService;

  @PostMapping("/add_microcycle_phase_per_user")
  public ResponseEntity<String> addMicrocyclePhasePerUser(
      @RequestBody NewDictMicrocyclePhaseDto newDictUnitDto
  ) {
    var userId = extractUserId();
    dictMicrocyclePhaseService.addDictUnit(newDictUnitDto, userId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/add_microcycle_phase_basic")
  public ResponseEntity<String> addMicrocyclePhaseBasic(
      @RequestBody NewDictMicrocyclePhaseDto newDictUnitDto
  ) {
    var role = extractUserRole();
    if (role.contains("ADMIN")) {
      dictMicrocyclePhaseService.addDictUnit(newDictUnitDto);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/get_microcycle_phase_per_user")
  public ResponseEntity<List<DictMicrocyclePhaseDto>> getMicrocyclePhasePerUser(
  ) {
    var userId = extractUserId();
    try {
      var result = dictMicrocyclePhaseService.getDictMicrocyclePhase(userId);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_microcycle_phase_per_user/{itemToDelete}")
  public ResponseEntity<String> deleteExercisePerUser(
      @PathVariable("itemToDelete") int itemToDelete
  ) {
    var userId = extractUserId();
    try {
      dictMicrocyclePhaseService.deleteDictMicrocyclePhasePerUser(userId, itemToDelete);
      return new ResponseEntity<>( HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_microcycle_phase_basic/{itemToDelete}")
  public ResponseEntity<String> deleteExerciseBasic(
      @PathVariable("itemToDelete") int itemToDelete
  ) {
    var userRole = extractUserRole();
    var userId = extractUserId();
    if (userRole.contains("ADMIN")) {
      try {
        dictMicrocyclePhaseService.deleteDictMicrocyclePhaseBasic(userId, itemToDelete);
        return new ResponseEntity<>( HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }
}
