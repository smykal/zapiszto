package com.zapiszto.controllers.program.programs.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.program.programs.dto.NewProgramDto;
import com.zapiszto.controllers.program.programs.dto.ProgramDto;
import com.zapiszto.controllers.program.programs.dto.ProgramNameDto;
import com.zapiszto.controllers.program.programs.service.ProgramsService;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
public class ProgramsController implements ControllerCommon {

  @Autowired
  ProgramsService programsService;

  @GetMapping("/get_programs")
  public ResponseEntity<List<ProgramDto>> getPrograms() {
    var trainerId = extractUserId();
    try {
      var programs = programsService.getPrograms(trainerId);
      return new ResponseEntity<>(
          programs,
          HttpStatus.OK
      );
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @PostMapping("/add_program")
  public ResponseEntity<String> saveProgram(
      @RequestBody NewProgramDto newProgramDto
  ) {
    var trainerId = extractUserId();
    try {
      programsService.addProgram(newProgramDto, trainerId);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
  }

  @PatchMapping("/update_program_name/{id}")
  public ResponseEntity<String> updateProgramName(
      @PathVariable UUID id,
      @RequestBody ProgramNameDto programNameDto) {
    programsService.updateProgramName(id, programNameDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/delete_program/{id}")
  public ResponseEntity<String> deleteProgram(
      @PathVariable UUID id) {
    var requestorId = extractUserId();
    programsService.deleteProgram(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
