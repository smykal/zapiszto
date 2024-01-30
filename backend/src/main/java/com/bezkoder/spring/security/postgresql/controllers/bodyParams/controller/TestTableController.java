package com.bezkoder.spring.security.postgresql.controllers.bodyParams.controller;

import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BodyParametersDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.service.TestTableService;
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
public class TestTableController {

  @Autowired
  private TestTableService testTableService;

  @PostMapping("/test_post")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> saveTestItem(
      @RequestBody BodyParametersDto bodyParametersDto
      ) {
    testTableService.saveTestItem(bodyParametersDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/test_get")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<BodyParametersDto>> getTestTable() {
    var userId = extractUserId();
    log.info("user co sie dobieral: {} ", userId);
    var response = testTableService.getTestTable(userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/actual_body_params")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<BodyParametersDto>> getActualBodyParams() {
    var userId = extractUserId();
    log.info("user co sie dobieral: {} ", userId);
    var response = testTableService.getActualBodyParameters(userId);
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
