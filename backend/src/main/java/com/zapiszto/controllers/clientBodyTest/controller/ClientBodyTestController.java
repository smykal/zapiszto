package com.zapiszto.controllers.clientBodyTest.controller;


import com.zapiszto.controllers.clientBodyTest.dto.NewClientBodyTestDto;
import com.zapiszto.controllers.clientBodyTest.service.ClientBodyTestService;
import com.zapiszto.controllers.common.ControllerCommon;
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
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class ClientBodyTestController implements ControllerCommon {

  @Autowired
  ClientBodyTestService clientBodyTestService;

  @PostMapping("/add_client_body_test")
  public ResponseEntity<String> addClientBodyTest(
      @RequestBody NewClientBodyTestDto newClientBodyTestDto
      ) {
    var trainerId = extractUserId();
    clientBodyTestService.addClientBodyTest(newClientBodyTestDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

}
