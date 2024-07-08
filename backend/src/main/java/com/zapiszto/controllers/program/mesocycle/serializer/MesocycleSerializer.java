package com.zapiszto.controllers.program.mesocycle.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.program.mesocycle.dto.MesocycleDto;
import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import com.zapiszto.controllers.program.microcycle.entity.MicrocycleEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MesocycleSerializer implements SerializerCommon {

  public MesocycleDto convert(MesocycleEntity mesocycleEntity) {
    String dictType;
    String dictName;
    if (isPerUserOrBasic(mesocycleEntity)) {
      dictType = PER_USER;
      dictName = mesocycleEntity.getDictMesocyclePhaseEntity().getDictMesocyclePhasePerUserEntity().getName();
    } else {
      dictType = BASIC;
      dictName = mesocycleEntity.getDictMesocyclePhaseEntity().getDictMesocyclePhaseBasicEntity().getName();
    }

    return MesocycleDto.builder()
        .id(mesocycleEntity.getId().toString())
        .macrocycleId(mesocycleEntity.getMacrocycleId().toString())
        .duration(mesocycleEntity.getDuration())
        .orderId(mesocycleEntity.getOrderId())
        .comments(mesocycleEntity.getComments())
        .dictType(dictType)
        .dictId(mesocycleEntity.getDictMesocyclePhaseId())
        .dictName(dictName)
        .label(mesocycleEntity.getLabel())
        .build();
  }

  public List<MesocycleDto> convertList(List<MesocycleEntity> mesocycleEntities) {
    return mesocycleEntities.stream()
        .map(this::convert)
        .collect(Collectors.toList());
  }

  private static boolean isPerUserOrBasic(MesocycleEntity mesocycleEntity) {
    return mesocycleEntity.getDictMesocyclePhaseEntity()
        .getDictMesocyclePhaseBasicEntity()
        .getName()
        .isEmpty();
  }
}
