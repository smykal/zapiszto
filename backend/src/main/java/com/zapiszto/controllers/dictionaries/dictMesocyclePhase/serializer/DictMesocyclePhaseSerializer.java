package com.zapiszto.controllers.dictionaries.dictMesocyclePhase.serializer;

import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dto.DictMesocyclePhaseDto;
import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.entity.DictMesocyclePhaseEntity;

public class DictMesocyclePhaseSerializer {
  private static final String PER_USER = "PER_USER";
  private static final String BASIC = "BASIC";

  public static DictMesocyclePhaseDto convert(DictMesocyclePhaseEntity dictMicrocyclePhase) {
    if(dictMicrocyclePhase.getDictMesocyclePhaseBasicEntity() != null) {
      return DictMesocyclePhaseDto.builder()
          .id(dictMicrocyclePhase.getId())
          .dict(BASIC)
          .dict_id(dictMicrocyclePhase.getDictMesocyclePhaseBasicEntity().getId())
          .name(dictMicrocyclePhase.getDictMesocyclePhaseBasicEntity().getName())
          .shortcut(dictMicrocyclePhase.getDictMesocyclePhaseBasicEntity().getShortcut())
          .build();
    } else {
      return DictMesocyclePhaseDto.builder()
          .id(dictMicrocyclePhase.getId())
          .dict(PER_USER)
          .dict_id(dictMicrocyclePhase.getDictMesocyclePhasePerUserEntity().getId())
          .name(dictMicrocyclePhase.getDictMesocyclePhasePerUserEntity().getName())
          .shortcut(dictMicrocyclePhase.getDictMesocyclePhasePerUserEntity().getShortcut())
          .build();
    }
  }
}
