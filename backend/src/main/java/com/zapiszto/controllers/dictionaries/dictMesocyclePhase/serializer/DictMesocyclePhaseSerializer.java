package com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.serializer;

import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dto.DictMicrocyclePhaseDto;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.entity.DictMicrocyclePhaseEntity;

public class DictMicrocyclePhaseSerializer {
  private static final String PER_USER = "PER_USER";
  private static final String BASIC = "BASIC";

  public static DictMicrocyclePhaseDto convert(DictMicrocyclePhaseEntity dictMicrocyclePhase) {
    if(dictMicrocyclePhase.getDictMicrocyclePhaseBasicEntity() != null) {
      return DictMicrocyclePhaseDto.builder()
          .id(dictMicrocyclePhase.getId())
          .dict(BASIC)
          .dict_id(dictMicrocyclePhase.getDictMicrocyclePhaseBasicEntity().getId())
          .name(dictMicrocyclePhase.getDictMicrocyclePhaseBasicEntity().getName())
          .shortcut(dictMicrocyclePhase.getDictMicrocyclePhaseBasicEntity().getShortcut())
          .build();
    } else {
      return DictMicrocyclePhaseDto.builder()
          .id(dictMicrocyclePhase.getId())
          .dict(PER_USER)
          .dict_id(dictMicrocyclePhase.getDictMicrocyclePhasePerUserEntity().getId())
          .name(dictMicrocyclePhase.getDictMicrocyclePhasePerUserEntity().getName())
          .shortcut(dictMicrocyclePhase.getDictMicrocyclePhasePerUserEntity().getShortcut())
          .build();
    }
  }
}
