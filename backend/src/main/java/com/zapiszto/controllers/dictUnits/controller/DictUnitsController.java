package com.zapiszto.controllers.dictUnits.controller;

import com.zapiszto.controllers.ControllerCommon;
import com.zapiszto.controllers.dictUnits.dto.DictUnitsDto;
import com.zapiszto.controllers.dictUnits.dto.NewDictUnitDto;
import com.zapiszto.controllers.dictUnits.service.DictUnitsService;
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
public class DictUnitsController implements ControllerCommon {

  @Autowired
  DictUnitsService dictUnitsService;

  @PostMapping("/add_units_per_user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> addUnitsPerUser(
      @RequestBody NewDictUnitDto newDictUnitDto
      ) {
    var userId = extractUserId();
    dictUnitsService.addDictUnit(newDictUnitDto, userId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/add_units_basic")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> addUnitsBasic(
      @RequestBody NewDictUnitDto newDictUnitDto
  ) {
    var role = extractUserRole();
    if (role.contains("ADMIN")){
      dictUnitsService.addDictUnit(newDictUnitDto);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/get_units_per_user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<DictUnitsDto>> getUnitsPerUser(
  ) {
    var userId = extractUserId();

    var result = dictUnitsService.getDictUnits(userId);
    if (!result.isEmpty()) {
      return new ResponseEntity<>(result,HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
}
