package com.zapiszto.controllers.dictionaries.dictEquipment.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.dictionaries.dictEquipment.dto.DictEquipmentDto;
import com.zapiszto.controllers.dictionaries.dictEquipment.dto.NewDictEquipmentDto;
import com.zapiszto.controllers.dictionaries.dictEquipment.service.DictEquipmentService;
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
public class DictEquipmentController implements ControllerCommon {

  @Autowired
  DictEquipmentService dictEquipmentService;

  @PostMapping("/add_equipment_per_user")
  public ResponseEntity<String> addUnitsPerUser(
      @RequestBody NewDictEquipmentDto newDictEquipmentDto
  ) {
    var userId = extractUserId();
    dictEquipmentService.addDictEquipment(newDictEquipmentDto, userId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/add_equipment_basic")
  public ResponseEntity<String> addUnitsBasic(
      @RequestBody NewDictEquipmentDto newDictEquipmentDto
  ) {
    var role = extractUserRole();
    if (role.contains("ADMIN")) {
      try {
        dictEquipmentService.addDictEquipment(newDictEquipmentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
      } catch (NullPointerException e) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }
    } else {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/get_equipment_per_user")
  public ResponseEntity<List<DictEquipmentDto>> getUnitsPerUser(
  ) {
    var userId = extractUserId();
    try {
      var result = dictEquipmentService.getDictEquipment(userId);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_equipment_per_user/{itemToDelete}")
  public ResponseEntity<String> deleteExercisePerUser(
      @PathVariable("itemToDelete") int itemToDelete
  ) {
    var userId = extractUserId();
    try {
      dictEquipmentService.deleteDictEquipmentPerUser(userId, itemToDelete);
      return new ResponseEntity<>( HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_equipment_basic/{itemToDelete}")
  public ResponseEntity<String> deleteExerciseBasic(
      @PathVariable("itemToDelete") int itemToDelete
  ) {
    var userRole = extractUserRole();
    var userId = extractUserId();
    if (userRole.contains("ADMIN")) {
      try {
        dictEquipmentService.deleteDictEquipmentBasic(userId, itemToDelete);
        return new ResponseEntity<>( HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }
}
