package com.zapiszto.controllers.program.periodization.controller;

import com.zapiszto.controllers.program.periodization.dto.PeriodizationDto;
import com.zapiszto.controllers.program.periodization.service.PeriodizationService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class PeriodizationController {

  @Autowired
  PeriodizationService periodizationService;

  @GetMapping("/periodization_distinct")
  public ResponseEntity<List<PeriodizationDto>> getDistinctNameAndDescription() {
    List<PeriodizationDto> periodizations = periodizationService.getDistinctNameAndDescription();
    return new ResponseEntity<>(periodizations, HttpStatus.OK);
  }
}
