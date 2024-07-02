package com.zapiszto.controllers.program.macrocycle.service;

import com.zapiszto.controllers.program.macrocycle.dto.MacrocycleDto;
import com.zapiszto.controllers.program.macrocycle.dto.NewMacrocycleDto;
import com.zapiszto.controllers.program.macrocycle.entity.MacrocycleEntity;
import com.zapiszto.controllers.program.macrocycle.repository.MacrocycleRepository;
import com.zapiszto.controllers.program.macrocycle.serializer.MacrocycleSerializer;
import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import com.zapiszto.controllers.program.mesocycle.service.MesocycleService;
import com.zapiszto.controllers.program.microcycle.entity.MicrocycleEntity;
import com.zapiszto.controllers.program.microcycle.service.MicrocycleService;
import com.zapiszto.controllers.program.sessions.service.SessionService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class MacrocycleService {
  @Autowired
  MacrocycleRepository macrocycleRepository;

  @Autowired
  MacrocycleSerializer macrocycleSerializer;

  @Autowired
  MesocycleService mesocycleService;

  @Autowired
  MicrocycleService microcycleService;

  @Autowired
  SessionService sessionService;

  @Transactional
  public MacrocycleEntity addMacrocycle(NewMacrocycleDto newMacrocycleDto) {
    UUID id = UUID.fromString(newMacrocycleDto.getId());
    UUID programId = UUID.fromString(newMacrocycleDto.getProgramId());
    int macrocycleDuration = newMacrocycleDto.getDuration()*4;
    int mesocycleDuration = newMacrocycleDto.getMesocycleDuration();
    String periodizationName = newMacrocycleDto.getPeriodization();
    int sessionsForMicrocycle = newMacrocycleDto.getSessionsForMicrocycle();
    int sessionDuration = newMacrocycleDto.getDurationSession();

    MacrocycleEntity macrocycleEntity = MacrocycleEntity.builder()
        .id(id)
        .programId(programId)
        .duration(newMacrocycleDto.getDuration())
        .durationLeft(macrocycleDuration)
        .build();
    MacrocycleEntity macrocycle = macrocycleRepository.save(macrocycleEntity);
    log.info("dodano nowy makrocykl o id {}", id);

    return macrocycle;
    //mesocycleService.addMesocycle(macrocycleDuration, mesocycleDuration, id, periodizationName, sessionsForMicrocycle, sessionDuration);
    //log.info("dodano odpowiednio mesocycle");
  }

  public MacrocycleDto getMacrocycle(UUID programId) {

    MacrocycleEntity macrocycleByProgramId = macrocycleRepository.getMacrocycleByProgramId(programId);
    return macrocycleSerializer.convert(macrocycleByProgramId);
  }

  public void addProgram(NewMacrocycleDto newMacrocycleDto) {
    MacrocycleEntity macrocycleEntity = addMacrocycle(newMacrocycleDto);

    List<MesocycleEntity> mesocycleEntities = mesocycleService.addMezocycle(macrocycleEntity, newMacrocycleDto);

    for (MesocycleEntity mesocycleEntity: mesocycleEntities) {
      List<MicrocycleEntity> microcycleEntities = microcycleService.addMicrocycles(mesocycleEntity, newMacrocycleDto);
      for (MicrocycleEntity microcycleEntity: microcycleEntities) {
        sessionService.addSessions(microcycleEntity, newMacrocycleDto);
      }

    }

  }
}
