package com.zapiszto.controllers.dictionaries.dictSessionPart.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.dictionaries.dictSessionPart.dto.DictSessionPartDto;
import com.zapiszto.controllers.dictionaries.dictSessionPart.dto.NewDictSessionPartDto;
import com.zapiszto.controllers.dictionaries.dictSessionPart.service.DictSessionPartService;
import com.zapiszto.controllers.dictionaries.dictUnits.dto.DictUnitsDto;
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
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class DictSessionPartController implements ControllerCommon {
  
  @Autowired
  DictSessionPartService dictSessionPartService;

  @PostMapping("/add_session_part_per_user")
  public ResponseEntity<String> addUnitsPerUser(
      @RequestBody NewDictSessionPartDto newDictSessionPartDto
  ) {
    var userId = extractUserId();
    dictSessionPartService.addDictSessionPart(newDictSessionPartDto, userId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/add_session_part_basic")
  public ResponseEntity<String> addUnitsBasic(
      @RequestBody NewDictSessionPartDto newDictSessionPartDto
  ) {
    var role = extractUserRole();
    if (role.contains("ADMIN")) {
      try {
        dictSessionPartService.addDictSessionPart(newDictSessionPartDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
      } catch (NullPointerException e) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }
    } else {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/get_session_part_per_user")
  public ResponseEntity<List<DictSessionPartDto>> getUnitsPerUser(
  ) {
    var userId = extractUserId();
    try {
      var result = dictSessionPartService.getDictSessionPart(userId);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_session_part_per_user/{itemToDelete}")
  public ResponseEntity<String> deleteExercisePerUser(
      @PathVariable("itemToDelete") int itemToDelete
  ) {
    var userId = extractUserId();
    try {
      dictSessionPartService.deleteDictSessionPartPerUser(userId, itemToDelete);
      return new ResponseEntity<>( HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_session_part_basic/{itemToDelete}")
  public ResponseEntity<String> deleteExerciseBasic(
      @PathVariable("itemToDelete") int itemToDelete
  ) {
    var userRole = extractUserRole();
    var userId = extractUserId();
    if (userRole.contains("ADMIN")) {
      try {
        dictSessionPartService.deleteDictSessionPartBasic(userId, itemToDelete);
        return new ResponseEntity<>( HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }
}
