package com.zapiszto.controllers.clientBodyTest.controller;


import com.zapiszto.controllers.clientBodyTest.dto.ClientBodyTestDto;
import com.zapiszto.controllers.clientBodyTest.dto.NewClientBodyTestDto;
import com.zapiszto.controllers.clientBodyTest.service.ClientBodyTestService;
import com.zapiszto.controllers.common.ControllerCommon;
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

  @GetMapping("/get_client_body_tests/{clientId}")
  public ResponseEntity<List<ClientBodyTestDto>> getClientBodyTests(
      @PathVariable("clientId") UUID clientId
  ) {
    var trainerId = extractUserId();
    try {
      List<ClientBodyTestDto> result = clientBodyTestService.getClientBodyTests(clientId, trainerId);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }
}
