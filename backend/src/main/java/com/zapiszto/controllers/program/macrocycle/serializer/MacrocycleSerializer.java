package com.zapiszto.controllers.program.macrocycle.serializer;

import com.zapiszto.controllers.program.macrocycle.dto.MacrocycleDto;
import com.zapiszto.controllers.program.macrocycle.entity.MacrocycleEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MacrocycleSerializer {

  public MacrocycleDto convert(MacrocycleEntity macrocycleEntity) {
    return MacrocycleDto.builder()
        .id(macrocycleEntity.getId().toString())
        .programId(macrocycleEntity.getProgramId().toString())
        .duration(macrocycleEntity.getDuration())
        .durationLeft(macrocycleEntity.getDurationLeft())
        .build();
  }
}
