package com.zapiszto.controllers.dictionaries.dictBodyTest.serializer;

import com.zapiszto.controllers.dictionaries.dictBodyTest.dto.DictBodyTestDto;
import com.zapiszto.controllers.dictionaries.dictBodyTest.entity.DictBodyTestEntity;
import com.zapiszto.controllers.common.SerializerCommon;

public class DictBodyTestSerializer implements SerializerCommon {

  public static DictBodyTestDto convert(DictBodyTestEntity dictBodyTestEntity) {

    if (dictBodyTestEntity.getDictBodyTestBasicEntity() != null) {
      return new DictBodyTestDto(
          dictBodyTestEntity.getId(),
          BASIC,
          dictBodyTestEntity.getDictBodyTestBasicEntity()
              .getId().toString(),
          dictBodyTestEntity.getDictBodyTestBasicEntity()
              .getName(),
          dictBodyTestEntity.getDictBodyTestBasicEntity()
              .getDescription(),
          dictBodyTestEntity.getDictBodyTestBasicEntity().getPurpose()
      );
    } else {
      return new DictBodyTestDto(
          dictBodyTestEntity.getId(),
          PER_USER,
          dictBodyTestEntity.getDictBodyTestPerUserEntity()
              .getId().toString(),
          dictBodyTestEntity.getDictBodyTestPerUserEntity()
              .getName(),
          dictBodyTestEntity.getDictBodyTestPerUserEntity()
              .getDescription(),
          dictBodyTestEntity.getDictBodyTestPerUserEntity()
              .getPurpose()
      );
    }
  }
}
