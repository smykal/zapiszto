package com.zapiszto.controllers.dictionaries.dictEquipment.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictEquipment.dto.DictEquipmentDto;
import com.zapiszto.controllers.dictionaries.dictEquipment.entity.DictEquipmentEntity;
import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.utilities.translator.Translations;

public class DictEquipmentSerializer extends Translations implements SerializerCommon {
  public static DictEquipmentDto convert(DictEquipmentEntity dictEquipment, Languages languages) {
    if (dictEquipment.getDictEquipmentBasicEntity() != null) {
      var name = dictEquipment.getDictEquipmentBasicEntity()
          .getName();


      return new DictEquipmentDto(
          dictEquipment.getId(),
          BASIC,
          dictEquipment.getDictEquipmentBasicEntity()
              .getId(),
          translate(name, languages)
      );
    } else {
      var name = dictEquipment.getDictEquipmentPerUserEntity()
          .getName();

      return new DictEquipmentDto(
          dictEquipment.getId(),
          PER_USER,
          dictEquipment.getDictEquipmentPerUserEntity()
              .getId(),
          translate(name, languages)
      );
    }
  }
}
