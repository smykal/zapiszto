package com.zapiszto.controllers.dictQuantityType.controller;

import com.zapiszto.controllers.ControllerCommon;
import com.zapiszto.controllers.dictQuantityType.dto.NewDictQuantityTypeDto;
import com.zapiszto.controllers.dictQuantityType.service.DictQuantityTypeService;
import com.zapiszto.controllers.dictQuantityType.dto.DictQuantityTypeDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
public class DictQuantityTypeController implements ControllerCommon {

  @Autowired
  DictQuantityTypeService dictQuantityTypeService;

  @PostMapping("/add_quantity_type_per_user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> addQuantityTypePerUser(
      @RequestBody NewDictQuantityTypeDto newDictUnitDto
  ) {
    var userId = extractUserId();
    dictQuantityTypeService.addDictUnit(newDictUnitDto, userId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/add_quantity_type_basic")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> addQuantityTypeBasic(
      @RequestBody NewDictQuantityTypeDto newDictUnitDto
  ) {
    var role = extractUserRole();
    if (role.contains("ADMIN")) {
      dictQuantityTypeService.addDictUnit(newDictUnitDto);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/get_quantity_type_per_user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<DictQuantityTypeDto>> getQuantityTypePerUser(
  ) {
    var userId = extractUserId();

    var result = dictQuantityTypeService.getDictQuantityType(userId);
    if (!result.isEmpty()) {
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
}
