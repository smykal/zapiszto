package com.zapiszto.controllers.program.clientProgramView.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.program.clientProgramView.dto.ClientProgramDto;
import com.zapiszto.controllers.program.clientProgramView.service.ClientProgramService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER') or hasRole('USER')")
@RequiredArgsConstructor
public class ClientProgramController implements ControllerCommon {

  private final ClientProgramService clientProgramService;

  @GetMapping("/get_client_program/{clientId}")
  public List<ClientProgramDto> getClientProgram(
      @PathVariable UUID clientId
  ) {
    return clientProgramService.getClientPrograms(clientId);
  }
}
