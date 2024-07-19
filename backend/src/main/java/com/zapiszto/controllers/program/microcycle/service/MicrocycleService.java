package com.zapiszto.controllers.program.microcycle.service;

import com.zapiszto.controllers.program.macrocycle.dto.NewMacrocycleDto;
import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import com.zapiszto.controllers.program.microcycle.dto.MicrocycleDto;
import com.zapiszto.controllers.program.microcycle.dto.MicrocycleStatsDto;
import com.zapiszto.controllers.program.microcycle.entity.MicrocycleEntity;
import com.zapiszto.controllers.program.microcycle.repository.MicrocycleRepository;
import com.zapiszto.controllers.program.microcycle.serializer.MicrocycleSerializer;
import com.zapiszto.controllers.program.sessions.service.SessionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class MicrocycleService {

  @Autowired
  MicrocycleRepository microcycleRepository;

  @Autowired
  SessionService sessionService;

  @Transactional
  public void addMicrocycles(int mesocycleDuration, UUID mesocycleId, int sessionsforMicrocycle, int sessionDuration) {
    int orderId = microcycleRepository.findMaxOrderIdByMesocycleId(mesocycleId)
        .orElse(0);

    List<MicrocycleEntity> microcycles = new ArrayList<>();
    int i = 0;
    while (i < mesocycleDuration) {
      orderId++;
      MicrocycleEntity microcycleEntity = MicrocycleEntity.builder()
          .id(UUID.randomUUID())
          .mesocycleId(mesocycleId)
          .orderId(orderId)
          .build();
      microcycles.add(microcycleEntity);
      i++;
    }
    //microcycleRepository.saveAll(microcycles);

    for (MicrocycleEntity microcycle : microcycles) {
      microcycleRepository.save(microcycle);
      sessionService.addSessions(sessionsforMicrocycle, microcycle.getId(), sessionDuration);
    }
  }

  public List<MicrocycleDto> getMicrocycle(UUID mesocycleId) {
    List<MicrocycleEntity> microcyclesByMesocycleId = microcycleRepository.getMicrocyclesByMesocycleId(mesocycleId);
    return microcyclesByMesocycleId.stream()
        .map(MicrocycleSerializer::convert)
        .collect(Collectors.toList());
  }

  public List<MicrocycleEntity> addMicrocycles(MesocycleEntity mesocycleEntity, NewMacrocycleDto newMacrocycleDto) {
    int orderId = microcycleRepository.findMaxOrderIdByMesocycleId(mesocycleEntity.getId())
        .orElse(0);

    List<MicrocycleEntity> microcycles = new ArrayList<>();
    int i = 0;
    while (i < newMacrocycleDto.getDuration()) {
      orderId++;
      MicrocycleEntity microcycleEntity = MicrocycleEntity.builder()
          .id(UUID.randomUUID())
          .mesocycleId(mesocycleEntity.getId())
          .orderId(orderId)
          .build();
      microcycles.add(microcycleEntity);
      i++;
    }

    List<MicrocycleEntity> microcycleEntities = microcycleRepository.saveAll(microcycles);
    return microcycleEntities;

  }

  public List<MicrocycleStatsDto> getMicrocycleStats(UUID microcycleId) {
    List<Object[]> microcycleStats = microcycleRepository.findMicrocycleStats(microcycleId);

    if (microcycleStats.isEmpty()) {
      return Collections.singletonList(new MicrocycleStatsDto(
          "",  // category
          null,  // repetitions
          null   // sets
      ));
    }

    return microcycleStats.stream()
        .map(result ->
            new MicrocycleStatsDto(
                (String) result[0],  // category
                (Long) result[1], // repetitions
                (Long) result[2]  // sets
            )
        )
        .collect(Collectors.toList());
  }
}
