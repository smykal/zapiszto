package com.zapiszto.controllers.program.mesocycle.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.program.mesocycle.dto.MesocycleDto;
import com.zapiszto.controllers.program.mesocycle.dto.NewMesocycleDto;
import com.zapiszto.controllers.program.mesocycle.dto.UpdateLabelDto;
import com.zapiszto.controllers.program.mesocycle.service.MesocycleService;
import com.zapiszto.controllers.program.programs.dto.ProgramNameDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class MesocycleController implements ControllerCommon {

  @Autowired
  MesocycleService mesocycleService;

  @PostMapping("/add_mesocycle")
  public ResponseEntity<String> saveMesocycle(
      @RequestBody NewMesocycleDto newMesocycleDto
  ) {

    var trainerId = extractUserId();
    try {
      mesocycleService.addMesocycle(newMesocycleDto);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/get_mesocycles/{macrocycleId}")
  public ResponseEntity<List<MesocycleDto>> getMesocycles(
      @PathVariable String macrocycleId
  ) {
    var trainerId = extractUserId();
    try {
      var mesocycles = mesocycleService.getMesocycles(macrocycleId);
      return new ResponseEntity<>(
          mesocycles,
          HttpStatus.OK
      );
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @PatchMapping("/update_mesocycle_label/{id}")
  public ResponseEntity<String> updateProgramName(
      @PathVariable String id,
      @RequestBody UpdateLabelDto updateLabelDto) {
    mesocycleService.updateMesocycleLabel(id, updateLabelDto.getLabel());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
