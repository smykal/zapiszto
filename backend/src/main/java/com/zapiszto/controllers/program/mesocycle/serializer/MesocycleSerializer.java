package com.zapiszto.controllers.program.mesocycle.serializer;

import com.zapiszto.controllers.program.mesocycle.dto.MesocycleDto;
import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MesocycleSerializer {

  public MesocycleDto convert(MesocycleEntity mesocycleEntity) {
    return MesocycleDto.builder()
        .id(mesocycleEntity.getId().toString())
        .macrocycleId(mesocycleEntity.getMacrocycleId().toString())
        .duration(mesocycleEntity.getDuration())
        .orderId(mesocycleEntity.getOrderId())
        .comments(mesocycleEntity.getComments())
        .build();
  }

  public List<MesocycleDto> convertList(List<MesocycleEntity> mesocycleEntities) {
    return mesocycleEntities.stream()
        .map(this::convert)
        .collect(Collectors.toList());
  }
}
