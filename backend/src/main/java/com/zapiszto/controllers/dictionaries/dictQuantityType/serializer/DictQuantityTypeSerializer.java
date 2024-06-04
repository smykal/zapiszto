package com.zapiszto.controllers.dictionaries.dictQuantityType.serializer;

import com.zapiszto.controllers.dictionaries.dictQuantityType.dto.DictQuantityTypeDto;
import com.zapiszto.controllers.dictionaries.dictQuantityType.entity.DictQuantityTypeEntity;

public class DictQuantityTypeSerializer {
  private static final String PER_USER = "PER_USER";
  private static final String BASIC = "BASIC";

  public static DictQuantityTypeDto convert(DictQuantityTypeEntity dictQuantityType) {
    if(dictQuantityType.getDictQuantityTypeBasicEntity() != null) {
      return DictQuantityTypeDto.builder()
          .id(dictQuantityType.getId())
          .dict(BASIC)
          .dict_id(dictQuantityType.getDictQuantityTypeBasicEntity().getId())
          .name(dictQuantityType.getDictQuantityTypeBasicEntity().getName())
          .shortcut(dictQuantityType.getDictQuantityTypeBasicEntity().getShortcut())
          .build();
    } else {
      return DictQuantityTypeDto.builder()
          .id(dictQuantityType.getId())
          .dict(PER_USER)
          .dict_id(dictQuantityType.getDictQuantityTypePerUserEntity().getId())
          .name(dictQuantityType.getDictQuantityTypePerUserEntity().getName())
          .shortcut(dictQuantityType.getDictQuantityTypePerUserEntity().getShortcut())
          .build();
    }
  }
}
