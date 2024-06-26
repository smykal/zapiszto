package com.zapiszto.controllers.program.microcycle.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.program.mesocycle.dto.MesocycleDto;
import com.zapiszto.controllers.program.microcycle.dto.MicrocycleDto;
import com.zapiszto.controllers.program.microcycle.service.MicrocycleService;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class MicrocycleController implements ControllerCommon {

  @Autowired
  MicrocycleService microcycleService;

  @GetMapping("/get_microcycles/{mesocycleId}")
  public ResponseEntity<List<MicrocycleDto>> getMicrocycles(
      @PathVariable UUID mesocycleId
  ) {
    var trainerId = extractUserId();
    try {
      var microcycles = microcycleService.getMicrocycle(mesocycleId);
      return new ResponseEntity<>(
          microcycles,
          HttpStatus.OK
      );
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }
}
