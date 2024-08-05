package com.zapiszto.controllers.dictionaries.dictBodyTest.controller;

import com.zapiszto.controllers.dictionaries.dictBodyTest.dto.DictBodyTestDto;
import com.zapiszto.controllers.dictionaries.dictBodyTest.dto.NewDictBodyTestDto;
import com.zapiszto.controllers.dictionaries.dictBodyTest.service.DictBodyTestService;
import com.zapiszto.controllers.common.ControllerCommon;
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
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class DictBodyTestController implements ControllerCommon {

  @Autowired
  DictBodyTestService dictBodyTestService;

  @PostMapping("/add_body_test_per_user")
  public ResponseEntity<DictBodyTestDto> addBodyTestPerUser(
      @RequestBody NewDictBodyTestDto dictBodyTestDto
  ) {
    var userId = extractUserId();
    DictBodyTestDto result = dictBodyTestService.addDictBodyTest(dictBodyTestDto, userId);
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @PostMapping("/add_body_test_basic")
  public ResponseEntity<String> addBodyTestBasic(
      @RequestBody NewDictBodyTestDto newDictBodyTestDto
  ) {
    var userId = extractUserId();
    var role = extractUserRole();
    dictBodyTestService.addDictBodyTest(newDictBodyTestDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/get_body_test_basic")
  public ResponseEntity<List<DictBodyTestDto>> getExerciseBasic(
  ) {
    var userId = extractUserId();
    try {
      var result = dictBodyTestService.getDictBodyTest(userId);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/get_body_test_per_user")
  public ResponseEntity<List<DictBodyTestDto>> getExercisesPerUser(
  ) {
    var userId = extractUserId();
    try {
      var result = dictBodyTestService.getDictBodyTest(userId);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_body_test_per_user/{itemToDelete}")
  public ResponseEntity<String> deleteCategoryPerUser(
      @PathVariable("itemToDelete") UUID itemToDelete
  ) {
    var userId = extractUserId();
    try {
      dictBodyTestService.deleteDictBodyTestPerUser(userId, itemToDelete);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_body_test_basic/{itemToDelete}")
  public ResponseEntity<String> deleteCategoryBasic(
      @PathVariable("itemToDelete") UUID itemToDelete
  ) {
    var userRole = extractUserRole();
    var userId = extractUserId();
    if (userRole.contains("ADMIN")) {
      try {
        dictBodyTestService.deleteDictBodyTestBasic(userId, itemToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }
}
