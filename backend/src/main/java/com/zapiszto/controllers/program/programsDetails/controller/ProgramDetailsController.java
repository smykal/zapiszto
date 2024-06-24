package com.zapiszto.controllers.program.programsDetails.controller;

import com.zapiszto.controllers.program.programsDetails.dto.ProgramDetailsAssignedClientDto;
import com.zapiszto.controllers.program.programsDetails.dto.ProgramDetailsDto;
import com.zapiszto.controllers.program.programsDetails.service.ProgramDetailsService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class ProgramDetailsController {

  @Autowired
  ProgramDetailsService programDetailsService;

  @GetMapping("/get_program_details/{programId}")
  public ResponseEntity<ProgramDetailsDto> getProgramDetails(@PathVariable String programId){
    try {
      var programDetails = programDetailsService.getProgramDetails(UUID.fromString(programId));
      return new ResponseEntity<>(programDetails, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @PatchMapping("/update_program")
  public ResponseEntity<String> updateProgramName(
      @RequestBody ProgramDetailsAssignedClientDto programDetailsAssignedClientDto
  ) {
    try {
      programDetailsService.updateProgramDetails(programDetailsAssignedClientDto);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      log.error("Error updating program details", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


}
