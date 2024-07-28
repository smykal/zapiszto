package com.zapiszto.controllers.bodyParams.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.bodyParams.dto.BodyMassIndexDto;
import com.zapiszto.controllers.bodyParams.dto.BodyParamsDto;
import com.zapiszto.controllers.bodyParams.dto.BodyParamsWithNameAndDateDto;
import com.zapiszto.controllers.bodyParams.dto.BodyParamsWithNameDto;
import com.zapiszto.controllers.bodyParams.service.BodyParamsService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class BodyParamsController implements ControllerCommon {

  @Autowired
  private BodyParamsService bodyParamsService;

  @GetMapping("/all_body_params")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<BodyParamsWithNameAndDateDto>> getAllBodyParams() {
    var userId = extractUserId();
    log.info("get all body parameters for user: {} ", userId);
    try {
      var response = bodyParamsService.getAllBodyParameters(userId);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/all_body_params/{userId}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
  public ResponseEntity<List<BodyParamsWithNameAndDateDto>> getAllBodyParamsById(
      @PathVariable("userId") Long userId
  ) {
    var trainerId = extractUserId();
    log.info("get all body params for user: {}  by trainer with id: {}", userId, trainerId);
    try {
      var response = bodyParamsService.getAllBodyParameters(userId, trainerId);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/actual_body_params")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<BodyParamsWithNameDto>> getActualBodyParams() {
    var userId = extractUserId();
    log.info("get actual body params for user: {} ", userId);
    try {
      var response = bodyParamsService.getActualBodyParametersWithName(userId);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/actual_body_params/{userId}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
  public ResponseEntity<List<BodyParamsWithNameDto>> getActualBodyParamsById(
      @PathVariable("userId") Long userId
  ) {
    var trainerId = extractUserId();
    log.info("get actual body params for user: {}  by trainer with id: {}", userId, trainerId);
    try {
      var response = bodyParamsService.getActualBodyParametersWithName(userId, trainerId);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/bmi")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<BodyMassIndexDto>> getBmi() {
    var userId = extractUserId();
    try {
      var response = bodyParamsService.getBodyMassIndex(userId);
      log.info("get bmi for user: {} ", userId);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

  }

  @PostMapping("/add_body_param")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
  public ResponseEntity<String> saveBodyParam(
     @Valid @RequestBody BodyParamsDto bodyParamsDto
  ) {
    var userId = extractUserId();
    try {
      bodyParamsService.saveBodyParam(bodyParamsDto, userId);
      log.info("post body parameters for user: {} ", userId);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
  }
}
