package com.bezkoder.spring.security.postgresql.controllers.trainings.controller;

import com.bezkoder.spring.security.postgresql.controllers.ControllerCommon;
import com.bezkoder.spring.security.postgresql.controllers.trainings.dto.NewTrainingDto;
import com.bezkoder.spring.security.postgresql.controllers.trainings.dto.TrainingDto;
import com.bezkoder.spring.security.postgresql.controllers.trainings.dto.TrainingNotesDto;
import com.bezkoder.spring.security.postgresql.controllers.trainings.service.TrainingsService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/get_trainings/workbook/{workbookId}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<TrainingDto>> getTrainings(
      @PathVariable("workbookId") Integer workbookId
  ) {
    var userId = extractUserId();
    try {
      List<TrainingDto> result = trainingsService.getTrainings(workbookId, userId);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @PatchMapping("/patch_trening_notes")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> patchTreningNotes(
      @RequestBody TrainingNotesDto trainingNotesDto
  ) {
    trainingsService.updateTrainingNotes(trainingNotesDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

}
