package com.zapiszto.controllers.clients.controller;

import com.zapiszto.controllers.clients.dto.ClientDto;
import com.zapiszto.controllers.clients.dto.NewClientDto;
import com.zapiszto.controllers.clients.service.ClientsService;
import com.zapiszto.controllers.common.ControllerCommon;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class ClientsController implements ControllerCommon {

  @Autowired
  ClientsService clientsService;

  @GetMapping("/get_clients")
  public ResponseEntity<List<ClientDto>> getClients() {
    var userId = extractUserId();
    try {
      var clients = clientsService.getClients(userId);
      return new ResponseEntity<>(
          clients,
          HttpStatus.OK
      );
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @PostMapping("/add_client")
  public ResponseEntity<String> saveClient(
      @RequestBody NewClientDto newClientDto
  ) {
    var userId = extractUserId();
    try {
      clientsService.addClient(newClientDto, userId);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
  }

  @PatchMapping("/update_client")
  public ResponseEntity<String> updateClient(@RequestBody ClientDto clientDto) {
    try {
      clientsService.updateClient(clientDto);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (RuntimeException e) {
      log.error("Error updating client: {}", e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}
