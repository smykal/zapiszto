package com.zapiszto.controllers.program.mesocycle.service;

import com.zapiszto.controllers.program.macrocycle.entity.MacrocycleEntity;
import com.zapiszto.controllers.program.macrocycle.repository.MacrocycleRepository;
import com.zapiszto.controllers.program.mesocycle.dto.MesocycleDto;
import com.zapiszto.controllers.program.mesocycle.dto.NewMesocycleDto;
import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import com.zapiszto.controllers.program.mesocycle.repository.MesocycleRepository;
import com.zapiszto.controllers.program.mesocycle.serializer.MesocycleSerializer;
import com.zapiszto.controllers.program.microcycle.repository.MicrocycleRepository;
import com.zapiszto.controllers.program.microcycle.service.MicrocycleService;
import java.util.ArrayList;
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
public class MesocycleService {
  @Autowired
  MesocycleRepository mesocycleRepository;

  @Autowired
  MesocycleSerializer mesocycleSerializer;

  @Autowired
  MacrocycleRepository macrocycleRepository;

  @Autowired
  MicrocycleService microcycleService;

  @Transactional
  public void addMesocycle(NewMesocycleDto newMesocycleDto) {
    UUID id = UUID.fromString(newMesocycleDto.getId());
    UUID macrocycleId = UUID.fromString(newMesocycleDto.getMacrocycleId());
    int mesocyleDuration = newMesocycleDto.getDuration();

    int orderId = mesocycleRepository.findMaxOrderIdByMacrocycleId(macrocycleId)
        .map(maxOrderId -> maxOrderId + 1)
        .orElse(1);

    MesocycleEntity mesocycleEntity = MesocycleEntity.builder()
        .id(id)
        .macrocycleId(macrocycleId)
        .duration(mesocyleDuration)
        .orderId(orderId)
        .comments(newMesocycleDto.getComments())
        .build();
    mesocycleRepository.save(mesocycleEntity);
    log.info("Dodano nowy mezocykl o id {} z orderId {}", id, orderId);

    MacrocycleEntity macrocycleByMacrocycleId = macrocycleRepository.getMacrocycleByMacrocycleId(macrocycleId);
    int durationLeft = macrocycleByMacrocycleId.getDurationLeft();
    durationLeft = durationLeft - mesocyleDuration;
    macrocycleByMacrocycleId.setDurationLeft(durationLeft);

    macrocycleRepository.save(macrocycleByMacrocycleId);
    log.info("update macrocycle id {}, durationLeft set {}", macrocycleId, durationLeft);

    microcycleService.addMicrocycles(mesocyleDuration, id);
  }

  public List<MesocycleDto> getMesocycles(String macrocycleId) {
    List<MesocycleEntity> mesocyclesByMacrocycleId = mesocycleRepository.getMesocyclesByMacrocycleId(UUID.fromString(macrocycleId));
    return mesocycleSerializer.convertList(mesocyclesByMacrocycleId);
  }

  public void addMesocycle(int macrocycleDuration, int mesocycleDuration, UUID macrocycleId) {
    int orderId = mesocycleRepository.findMaxOrderIdByMacrocycleId(macrocycleId)
        .map(maxOrderId -> maxOrderId + 1)
        .orElse(1);

    List<MesocycleEntity> mesocycles = new ArrayList<>();
    int remainingDuration = macrocycleDuration;
    while (remainingDuration > 0) {
      int currentMesocycleDuration = Math.min(mesocycleDuration, remainingDuration);
      MesocycleEntity mesocycle = MesocycleEntity.builder()
          .id(UUID.randomUUID())
          .macrocycleId(macrocycleId)
          .duration(currentMesocycleDuration)
          .dictMesocyclePhaseId(1)
          .orderId(orderId)
          .build();

      mesocycles.add(mesocycle);
      remainingDuration -= currentMesocycleDuration;
      orderId++;
    }
    mesocycleRepository.saveAll(mesocycles);

    for (MesocycleEntity mesocycle : mesocycles) {
      microcycleService.addMicrocycles(mesocycle.getDuration(), mesocycle.getId());
    }
  }
}
