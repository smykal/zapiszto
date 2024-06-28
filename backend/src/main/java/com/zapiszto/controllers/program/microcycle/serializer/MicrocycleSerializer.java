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
    String dictType;
    String dictName;

    if (isPerUserOrBasic(microcycleEntity)) {
      dictType = PER_USER;
      dictName = microcycleEntity.getDictMicrocyclePhase().getDictMicrocyclePhasePerUserEntity().getName();
    } else {
      dictType = BASIC;
      dictName = microcycleEntity.getDictMicrocyclePhase().getDictMicrocyclePhaseBasicEntity().getName();
    }
    return MicrocycleDto.builder()
        .id(microcycleEntity.getId().toString())
        .mesocycleId(microcycleEntity.getMesocycleId().toString())
        .orderId(microcycleEntity.getOrderId())
        .dictType(dictType)
        .dictId(microcycleEntity.getDictMicrocyclePhaseId())
        .dictName(dictName)
        .build();
  }

  private static boolean isPerUserOrBasic(MicrocycleEntity microcycleEntity) {
    return microcycleEntity.getDictMicrocyclePhase()
        .getDictMicrocyclePhaseBasicEntity()
        .getName()
        .isEmpty();
  }
}
