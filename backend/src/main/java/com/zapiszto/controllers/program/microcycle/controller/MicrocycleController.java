package com.zapiszto.controllers.program.microcycle.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.program.microcycle.dto.MicrocycleDto;
import com.zapiszto.controllers.program.microcycle.dto.MicrocycleStatsDto;
import com.zapiszto.controllers.program.microcycle.service.MicrocycleService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("get_microcycle_stats/{microcycleId}")
  public ResponseEntity<List<MicrocycleStatsDto>> getMicrocycleStats(
      @PathVariable UUID microcycleId
  ) {
    var trainerId = extractUserId();
    try {
      var result = microcycleService.getMicrocycleStats(microcycleId);
      return new ResponseEntity<>(
          result,
          HttpStatus.OK
      );
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @PostMapping("/add_microcycle/{mesocycleId}")
  public ResponseEntity<String> addMicrocycle(
      @PathVariable UUID mesocycleId
  ) {
    try {
      microcycleService.addMicrocycle(mesocycleId);
      return new ResponseEntity<>("Microcycle created successfully", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Error creating microcycle", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PatchMapping("/update_microcycle_share/{microcycleId}")
  public ResponseEntity<String> updateShare(
      @PathVariable UUID microcycleId,
      @RequestParam boolean share
  ) {
    microcycleService.updateShare(microcycleId, share);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  @DeleteMapping("/delete_microcycle/{microcycleId}")
  public ResponseEntity<String> deleteMicrocycle(
      @PathVariable UUID microcycleId
  ) {
    try {
      microcycleService.deleteMicrocycle(microcycleId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Error deleting microcycle", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


}
