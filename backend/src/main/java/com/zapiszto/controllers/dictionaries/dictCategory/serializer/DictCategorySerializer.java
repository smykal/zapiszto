package com.zapiszto.controllers.dictionaries.dictCategory.serializer;

import com.zapiszto.controllers.dictionaries.dictCategory.dto.DictCategoryDto;
import com.zapiszto.controllers.dictionaries.dictCategory.entity.DictCategoryEntity;

public class DictCategorySerializer {
  private static final String PER_USER = "PER_USER";
  private static final String BASIC = "BASIC";

  public static DictCategoryDto convert(DictCategoryEntity dictCategoryEntity) {

    if (dictCategoryEntity.getDictCategoryBasicEntity() != null) {
      return DictCategoryDto.builder()
          .id(dictCategoryEntity.getId())
          .dict(BASIC)
          .dict_id(dictCategoryEntity.getDictCategoryBasicEntity()
              .getId())
          .name(dictCategoryEntity.getDictCategoryBasicEntity()
              .getName())
          .description(dictCategoryEntity.getDictCategoryBasicEntity()
              .getDescription())
          .build();
    } else {
      return DictCategoryDto.builder()
          .id(dictCategoryEntity.getId())
          .dict(PER_USER)
          .dict_id(dictCategoryEntity.getDictCategoryPerUserEntity()
              .getId())
          .name(dictCategoryEntity.getDictCategoryPerUserEntity()
              .getName())
          .description(dictCategoryEntity.getDictCategoryPerUserEntity()
              .getDescription())
          .build();
    }
  }
}
