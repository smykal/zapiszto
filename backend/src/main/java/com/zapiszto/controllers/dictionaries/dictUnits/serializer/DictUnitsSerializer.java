package com.zapiszto.controllers.dictUnits.serializer;

import com.zapiszto.controllers.dictUnits.dto.DictUnitsDto;
import com.zapiszto.controllers.dictUnits.entity.DictUnitsEntity;

public class DictUnitsSerializer {

  private static final String PER_USER = "PER_USER";
  private static final String BASIC = "BASIC";

  public static DictUnitsDto convert(DictUnitsEntity dictUnitsEntity) {

    if(dictUnitsEntity.getDictUnitsBasicEntity() != null) {
      return DictUnitsDto.builder()
          .id(dictUnitsEntity.getId())
          .dict(BASIC)
          .dict_id(dictUnitsEntity.getDictUnitsBasicEntity().getId())
          .name(dictUnitsEntity.getDictUnitsBasicEntity().getName())
          .shortcut(dictUnitsEntity.getDictUnitsBasicEntity().getShortcut())
          .build();
    } else {
      return DictUnitsDto.builder()
          .id(dictUnitsEntity.getId())
          .dict(PER_USER)
          .dict_id(dictUnitsEntity.getDictUnitsPerUserEntity().getId())
          .name(dictUnitsEntity.getDictUnitsPerUserEntity().getName())
          .shortcut(dictUnitsEntity.getDictUnitsPerUserEntity().getShortcut())
          .build();
    }
  }
}
