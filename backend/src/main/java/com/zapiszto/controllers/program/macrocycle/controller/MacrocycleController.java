package com.zapiszto.controllers.program.macrocycle.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.program.macrocycle.dto.MacrocycleDto;
import com.zapiszto.controllers.program.macrocycle.dto.NewMacrocycleDto;
import com.zapiszto.controllers.program.macrocycle.service.MacrocycleService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class MacrocycleController implements ControllerCommon {

  @Autowired
  MacrocycleService macrocycleService;

  @PostMapping("/add_macrocycle")
  public ResponseEntity<String> saveMacrocycle(
      @RequestBody NewMacrocycleDto newMacrocycleDto
      ) {

    var trainerId = extractUserId();
    try {
      macrocycleService.addMacrocycle(newMacrocycleDto);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/get_macrocycle/{programId}")
  public ResponseEntity<MacrocycleDto> getMacrocycle(
      @PathVariable UUID programId
  ) {
    var trainerId = extractUserId();
    try {
      var macrocycle = macrocycleService.getMacrocycle(programId);
      return new ResponseEntity<>(
          macrocycle,
          HttpStatus.OK
      );
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }
}
