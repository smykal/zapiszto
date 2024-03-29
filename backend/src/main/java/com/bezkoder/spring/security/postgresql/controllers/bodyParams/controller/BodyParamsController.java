package com.bezkoder.spring.security.postgresql.controllers.bodyParams.controller;

import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BodyMassIndexDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BodyParamsDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BodyParamsWithNameAndDateDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BodyParamsWithNameDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.service.BodyParamsService;
import com.bezkoder.spring.security.postgresql.security.services.UserDetailsImpl;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class BodyParamsController {

  @Autowired
  private BodyParamsService bodyParamsService;

  @PostMapping("/add_body_param")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> saveTestItem(
      @RequestBody BodyParamsDto bodyParamsDto
      ) {
    var userId = extractUserId();
    bodyParamsService.saveBodyParam(bodyParamsDto);
    log.info("get all body parameters for user: {} ", userId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/all_body_params")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<BodyParamsWithNameAndDateDto>> getTestTable() {
    var userId = extractUserId();
    log.info("get all body parameters for user: {} ", userId);
    var response = bodyParamsService.getAllBodyParameters(userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/actual_body_params")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<BodyParamsWithNameDto>> getActualBodyParams() {
    var userId = extractUserId();
    log.info("get actual body params for user: {} ", userId);
    var response = bodyParamsService.getActualBodyParametersWithName(userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/bmi")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<BodyMassIndexDto>> getBmi() {
    var userId = extractUserId();
    log.info("get bmi for user: {} ", userId);
    var response = bodyParamsService.getBodyMassIndex(userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  private Long extractUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof com.bezkoder.spring.security.postgresql.security.services.UserDetailsImpl user) {
      return  ((UserDetailsImpl) authentication.getPrincipal()).getId();
          //user.getUsername(); // Assuming username is the user ID
    }
    return null;
  }
}
