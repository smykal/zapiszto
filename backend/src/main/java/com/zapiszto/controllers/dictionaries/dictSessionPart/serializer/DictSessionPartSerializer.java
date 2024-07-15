package com.zapiszto.controllers.dictionaries.dictSessionPart.serializer;

import com.zapiszto.controllers.dictionaries.dictSessionPart.dto.DictSessionPartDto;
import com.zapiszto.controllers.dictionaries.dictSessionPart.entity.DictSessionPartEntity;

public class DictSessionPartSerializer {
  private static final String PER_USER = "PER_USER";
  private static final String BASIC = "BASIC";

  public static DictSessionPartDto convert(DictSessionPartEntity dictSessionPart) {
    if(dictSessionPart.getDictSessionPartBasicEntity() != null) {
      return DictSessionPartDto.builder()
          .id(dictSessionPart.getId())
          .dict(BASIC)
          .dict_id(dictSessionPart.getDictSessionPartBasicEntity().getId())
          .name(dictSessionPart.getDictSessionPartBasicEntity().getName())
          .build();
    } else {
      return DictSessionPartDto.builder()
          .id(dictSessionPart.getId())
          .dict(PER_USER)
          .dict_id(dictSessionPart.getDictSessionPartPerUserEntity().getId())
          .name(dictSessionPart.getDictSessionPartPerUserEntity().getName())
          .build();
    }
  }
}