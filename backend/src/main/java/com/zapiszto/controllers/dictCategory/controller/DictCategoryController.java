package com.zapiszto.controllers.dictCategory.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.dictCategory.dto.DictCategoryDto;
import com.zapiszto.controllers.dictCategory.dto.NewDictCategoryDto;
import com.zapiszto.controllers.dictCategory.service.DictCategoryService;
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
public class DictCategoryController implements ControllerCommon {

  @Autowired
  DictCategoryService dictCategoryService;

  @PostMapping("/add_category_per_user")
  public ResponseEntity<DictCategoryDto> addCategoryPerUser(
      @RequestBody NewDictCategoryDto newDictCategoryDto
  ) {
    var userId = extractUserId();
    DictCategoryDto result = dictCategoryService.addDictCategory(newDictCategoryDto, userId);
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @PostMapping("/add_category_basic")
  public ResponseEntity<String> addCategoryBasic(
      @RequestBody NewDictCategoryDto newDictCategoryDto
  ) {
    var userId = extractUserId();
    var role = extractUserRole();
    dictCategoryService.addDictCategory(newDictCategoryDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/get_category_basic")
  public ResponseEntity<List<DictCategoryDto>> getExerciseBasic(
  ) {
    var userId = extractUserId();
    try {
      var result = dictCategoryService.getDictCategory(userId);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

  }

  @GetMapping("/get_category_per_user")
  public ResponseEntity<List<DictCategoryDto>> getExercisesPerUser(
  ) {
    var userId = extractUserId();
    try {
      var result = dictCategoryService.getDictCategory(userId);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_category_per_user/{itemToDelete}")
  public ResponseEntity<String> deleteCategoryPerUser(
      @PathVariable("itemToDelete") int itemToDelete
  ) {
    var userId = extractUserId();
    try {
      dictCategoryService.deleteDictCategoryPerUser(userId, itemToDelete);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_category_basic/{itemToDelete}")
  public ResponseEntity<String> deleteCategoryBasic(
      @PathVariable("itemToDelete") int itemToDelete
  ) {
    var userRole = extractUserRole();
    var userId = extractUserId();
    if (userRole.contains("ADMIN")) {
      try {
        dictCategoryService.deleteDictCategoryBasic(userId, itemToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }
}
