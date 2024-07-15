package com.zapiszto.controllers.dictionaries.dictUnits.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.dictionaries.dictUnits.dto.DictUnitsDto;
import com.zapiszto.controllers.dictionaries.dictUnits.dto.NewDictUnitDto;
import com.zapiszto.controllers.dictionaries.dictUnits.service.DictUnitsService;
import java.util.List;
import java.util.UUID;
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
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class DictUnitsController implements ControllerCommon {

  @Autowired
  DictUnitsService dictUnitsService;

  @PostMapping("/add_units_per_user")
  public ResponseEntity<String> addUnitsPerUser(
      @RequestBody NewDictUnitDto newDictUnitDto
  ) {
    var userId = extractUserId();
    dictUnitsService.addDictUnit(newDictUnitDto, userId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/add_units_basic")
  public ResponseEntity<String> addUnitsBasic(
      @RequestBody NewDictUnitDto newDictUnitDto
  ) {
    var role = extractUserRole();
    if (role.contains("ADMIN")) {
      try {
        dictUnitsService.addDictUnit(newDictUnitDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
      } catch (NullPointerException e) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }
    } else {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/get_units_per_user")
  public ResponseEntity<List<DictUnitsDto>> getUnitsPerUser(
  ) {
    var userId = extractUserId();
    try {
      var result = dictUnitsService.getDictUnits(userId);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_unit_per_user/{itemToDelete}")
  public ResponseEntity<String> deleteExercisePerUser(
      @PathVariable("itemToDelete") UUID itemToDelete
  ) {
    var userId = extractUserId();
    try {
      dictUnitsService.deleteDictUnitPerUser(userId, itemToDelete);
      return new ResponseEntity<>( HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_unit_basic/{itemToDelete}")
  public ResponseEntity<String> deleteExerciseBasic(
      @PathVariable("itemToDelete") UUID itemToDelete
  ) {
    var userRole = extractUserRole();
    var userId = extractUserId();
    if (userRole.contains("ADMIN")) {
      try {
        dictUnitsService.deleteDictUnitBasic(userId, itemToDelete);
        return new ResponseEntity<>( HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }
}
