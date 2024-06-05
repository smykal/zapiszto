package com.zapiszto.controllers.dictionaries.dictBodyTest.serializer;

import com.zapiszto.controllers.dictionaries.dictBodyTest.dto.DictBodyTestDto;
import com.zapiszto.controllers.dictionaries.dictBodyTest.entity.DictBodyTestEntity;
import com.zapiszto.controllers.common.SerializerCommon;

public class DictBodyTestSerializer implements SerializerCommon {

  public static DictBodyTestDto convert(DictBodyTestEntity dictBodyTestEntity) {

    if (dictBodyTestEntity.getDictBodyTestBasicEntity() != null) {
      return DictBodyTestDto.builder()
          .id(dictBodyTestEntity.getId())
          .dict(BASIC)
          .dict_id(dictBodyTestEntity.getDictBodyTestBasicEntity()
              .getId())
          .name(dictBodyTestEntity.getDictBodyTestBasicEntity()
              .getName())
          .description(dictBodyTestEntity.getDictBodyTestBasicEntity()
              .getDescription())
          .purpose(dictBodyTestEntity.getDictBodyTestBasicEntity().getPurpose())
          .build();
    } else {
      return DictBodyTestDto.builder()
          .id(dictBodyTestEntity.getId())
          .dict(PER_USER)
          .dict_id(dictBodyTestEntity.getDictBodyTestPerUserEntity()
              .getId())
          .name(dictBodyTestEntity.getDictBodyTestPerUserEntity()
              .getName())
          .description(dictBodyTestEntity.getDictBodyTestPerUserEntity()
              .getDescription())
          .purpose(dictBodyTestEntity.getDictBodyTestPerUserEntity()
              .getPurpose())
          .build();
    }
  }
}
