package com.zapiszto.controllers.dictionaries.dictQuantityType.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.dictionaries.dictQuantityType.dto.NewDictQuantityTypeDto;
import com.zapiszto.controllers.dictionaries.dictQuantityType.service.DictQuantityTypeService;
import com.zapiszto.controllers.dictionaries.dictQuantityType.dto.DictQuantityTypeDto;
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
public class DictQuantityTypeController implements ControllerCommon {

  @Autowired
  DictQuantityTypeService dictQuantityTypeService;

  @PostMapping("/add_quantity_type_per_user")
  public ResponseEntity<String> addQuantityTypePerUser(
      @RequestBody NewDictQuantityTypeDto newDictUnitDto
  ) {
    var userId = extractUserId();
    dictQuantityTypeService.addDictQuantityType(newDictUnitDto, userId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/add_quantity_type_basic")
  public ResponseEntity<String> addQuantityTypeBasic(
      @RequestBody NewDictQuantityTypeDto newDictUnitDto
  ) {
    var role = extractUserRole();
    if (role.contains("ADMIN")) {
      dictQuantityTypeService.addDictQuantityType(newDictUnitDto);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/get_quantity_type_per_user")
  public ResponseEntity<List<DictQuantityTypeDto>> getQuantityTypePerUser(
  ) {
    var userId = extractUserId();
    try {
      var result = dictQuantityTypeService.getDictQuantityType(userId);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_quantity_type_per_user/{itemToDelete}")
  public ResponseEntity<String> deleteExercisePerUser(
      @PathVariable("itemToDelete") UUID itemToDelete
  ) {
    var userId = extractUserId();
    try {
      dictQuantityTypeService.deleteDictQuantityTypePerUser(userId, itemToDelete);
      return new ResponseEntity<>( HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_quantity_type_basic/{itemToDelete}")
  public ResponseEntity<String> deleteExerciseBasic(
      @PathVariable("itemToDelete") UUID itemToDelete
  ) {
    var userRole = extractUserRole();
    var userId = extractUserId();
    if (userRole.contains("ADMIN")) {
      try {
        dictQuantityTypeService.deleteDictQuantityTypeBasic(userId, itemToDelete);
        return new ResponseEntity<>( HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }
}
