package com.zapiszto.controllers.program.sessions.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.program.sessions.dto.SessionDto;
import com.zapiszto.controllers.program.sessions.service.SessionService;
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
public class SessionController implements ControllerCommon {

  @Autowired
  private SessionService sessionService;

  @GetMapping("/get_sessions/{microcycleId}")
  public ResponseEntity<List<SessionDto>> getSessions(
      @PathVariable UUID microcycleId
  ) {
    var trainerId = extractUserId();
    try {
      var sessions = sessionService.getSessions(microcycleId);
      return new ResponseEntity<>(
          sessions,
          HttpStatus.OK
      );
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }
}