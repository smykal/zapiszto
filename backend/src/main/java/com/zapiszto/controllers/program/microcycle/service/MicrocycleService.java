package com.zapiszto.controllers.program.microcycle.service;

import com.zapiszto.controllers.program.microcycle.dto.MicrocycleDto;
import com.zapiszto.controllers.program.microcycle.entity.MicrocycleEntity;
import com.zapiszto.controllers.program.microcycle.repository.MicrocycleRepository;
import com.zapiszto.controllers.program.microcycle.serializer.MicrocycleSerializer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MicrocycleService {

  @Autowired
  MicrocycleRepository microcycleRepository;

  public void addMicrocycles(int mesocycleDuration, UUID mesocycleId) {
    int orderId = microcycleRepository.findMaxOrderIdByMesocycleId(mesocycleId).orElse(0);

    for (int i = 0; i < mesocycleDuration; i++) {
      orderId++;
      MicrocycleEntity microcycleEntity = MicrocycleEntity.builder()
          .id(UUID.randomUUID())
          .mesocycleId(mesocycleId)
          .orderId(orderId)
          .build();
      microcycleRepository.save(microcycleEntity);
    }
  }

  public List<MicrocycleDto> getMicrocycle(UUID mesocycleId){
    List<MicrocycleEntity> microcyclesByMesocycleId = microcycleRepository.getMicrocyclesByMesocycleId(mesocycleId);
    return microcyclesByMesocycleId.stream()
        .map(MicrocycleSerializer::convert)
        .collect(Collectors.toList());
  }
}
