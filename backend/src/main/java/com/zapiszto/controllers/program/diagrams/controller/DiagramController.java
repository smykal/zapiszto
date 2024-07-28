package com.zapiszto.controllers.program.diagrams.controller;

import com.zapiszto.controllers.program.diagrams.dto.ExerciseStatsDto;
import com.zapiszto.controllers.program.diagrams.service.DiagramService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class DiagramController {

  @Autowired
  DiagramService diagramService;

  @GetMapping("/get_exercise_stats/{mesocycleId}")
  public ResponseEntity<List<ExerciseStatsDto>> getExerciseStats(
      @PathVariable UUID mesocycleId) {
    try {
      var stats = diagramService.getExerciseStats(mesocycleId);
      return new ResponseEntity<>(stats, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Error retrieving exercise stats", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
