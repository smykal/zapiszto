package com.zapiszto.controllers.program.mesocycle.service;

import com.zapiszto.controllers.program.mesocycle.dto.MesocycleDto;
import com.zapiszto.controllers.program.mesocycle.dto.NewMesocycleDto;
import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import com.zapiszto.controllers.program.mesocycle.repository.MesocycleRepository;
import com.zapiszto.controllers.program.mesocycle.serializer.MesocycleSerializer;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MesocycleService {
  @Autowired
  MesocycleRepository mesocycleRepository;

  @Autowired
  MesocycleSerializer mesocycleSerializer;

  public void addMesocycle(NewMesocycleDto newMesocycleDto) {
    UUID id = UUID.fromString(newMesocycleDto.getId());
    UUID macrocycleId = UUID.fromString(newMesocycleDto.getMacrocycleId());

    int orderId = mesocycleRepository.findMaxOrderIdByMacrocycleId(macrocycleId)
        .map(maxOrderId -> maxOrderId + 1)
        .orElse(1);

    MesocycleEntity mesocycleEntity = MesocycleEntity.builder()
        .id(id)
        .macrocycleId(macrocycleId)
        .duration(newMesocycleDto.getDuration())
        .orderId(orderId)
        .comments(newMesocycleDto.getComments())
        .build();
    mesocycleRepository.save(mesocycleEntity);
    log.info("Dodano nowy mezocykl o id {} z orderId {}", id, orderId);
  }

  public List<MesocycleDto> getMesocycles(UUID macrocycleId) {
    List<MesocycleEntity> mesocyclesByMacrocycleId = mesocycleRepository.getMesocyclesByMacrocycleId(macrocycleId);
    return mesocycleSerializer.convertList(mesocyclesByMacrocycleId);
  }
}
