package com.zapiszto.controllers.goals.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.goals.dto.GoalDetailsDto;
import com.zapiszto.controllers.goals.dto.GoalDto;
import com.zapiszto.controllers.goals.dto.NewGoalDto;
import com.zapiszto.controllers.goals.service.GoalsService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class GoalsController implements ControllerCommon {

  @Autowired
  GoalsService goalsService;

  @PostMapping("/add_goal")
  public ResponseEntity<String> addGoal(
      @RequestBody NewGoalDto newGoalDto
  ) {
    var trainerId = extractUserId();
    goalsService.addGoal(newGoalDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/get_goals/{clientId}")
  public ResponseEntity<List<GoalDto>> getGoals(
      @PathVariable("clientId") UUID clientId
      ) {
    var requestorId = extractUserId();
    try {
      List<GoalDto> result = goalsService.getGoals(clientId);
      return new ResponseEntity<>(result, HttpStatus.OK);
    }catch (NullPointerException e) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
  @GetMapping("/get_goals_details/{clientId}")
  public ResponseEntity<List<GoalDetailsDto>> getGoalDetails(
      @PathVariable("clientId") UUID clientId
  ) {
    var requestorId = extractUserId();
    try {
      List<GoalDetailsDto> result = goalsService.getGoalDetails(clientId);
      return new ResponseEntity<>(result, HttpStatus.OK);
    }catch (NullPointerException e) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }

}
