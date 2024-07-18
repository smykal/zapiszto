package com.zapiszto.controllers.dictionaries.dictEquipment.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictEquipment.dto.DictEquipmentDto;
import com.zapiszto.controllers.dictionaries.dictEquipment.entity.DictEquipmentEntity;

public class DictEquipmentSerializer implements SerializerCommon {
  public static DictEquipmentDto convert(DictEquipmentEntity dictEquipment) {
    if(dictEquipment.getDictEquipmentBasicEntity() != null) {
      return DictEquipmentDto.builder()
          .id(dictEquipment.getId())
          .dict(BASIC)
          .dict_id(dictEquipment.getDictEquipmentBasicEntity().getId())
          .name(dictEquipment.getDictEquipmentBasicEntity().getName())
          .build();
    } else {
      return DictEquipmentDto.builder()
          .id(dictEquipment.getId())
          .dict(PER_USER)
          .dict_id(dictEquipment.getDictEquipmentPerUserEntity().getId())
          .name(dictEquipment.getDictEquipmentPerUserEntity().getName())
          .build();
    }
  }
}
