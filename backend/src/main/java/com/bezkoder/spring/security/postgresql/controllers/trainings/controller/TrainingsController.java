package com.bezkoder.spring.security.postgresql.controllers.trainings.controller;

import com.bezkoder.spring.security.postgresql.controllers.ControllerCommon;
import com.bezkoder.spring.security.postgresql.controllers.trainings.dto.NewTrainingDto;
import com.bezkoder.spring.security.postgresql.controllers.trainings.service.TrainingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
public class TrainingsController implements ControllerCommon {

  @Autowired
  private TrainingsService trainingsService;

  @PostMapping("/add_training")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> addTraining(
      @RequestBody NewTrainingDto newTrainingDto
  ) {
    trainingsService.addTraining(
        newTrainingDto
    );
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
