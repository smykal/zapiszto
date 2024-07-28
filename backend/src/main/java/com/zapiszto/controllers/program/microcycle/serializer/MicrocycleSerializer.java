package com.zapiszto.controllers.program.microcycle.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.program.microcycle.dto.MicrocycleDto;
import com.zapiszto.controllers.program.microcycle.entity.MicrocycleEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class MicrocycleSerializer implements SerializerCommon {

  public static MicrocycleDto convert(MicrocycleEntity microcycleEntity) {
    return new MicrocycleDto(
        microcycleEntity.getId(),
        microcycleEntity.getMesocycleId(),
        microcycleEntity.getOrderId(),
        microcycleEntity.isShare()
    );
  }
}
