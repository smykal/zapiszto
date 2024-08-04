package com.zapiszto.controllers.dictionaries.dictQuantityType.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.controllers.dictionaries.dictQuantityType.dto.DictQuantityTypeDto;
import com.zapiszto.controllers.dictionaries.dictQuantityType.entity.DictQuantityTypeEntity;
import com.zapiszto.utilities.translator.Translations;

public class DictQuantityTypeSerializer extends Translations implements SerializerCommon {

  public static DictQuantityTypeDto convert(DictQuantityTypeEntity dictQuantityType, Languages language) {
    if (dictQuantityType.getDictQuantityTypeBasicEntity() != null) {
      var name = dictQuantityType.getDictQuantityTypeBasicEntity()
          .getName();
      var shortcut = dictQuantityType.getDictQuantityTypeBasicEntity()
          .getShortcut();

      return new DictQuantityTypeDto(
          dictQuantityType.getId(),
          BASIC,
          dictQuantityType.getDictQuantityTypeBasicEntity()
              .getId(),
          translate(name, language),
          translate(shortcut, language)
      );
    } else {
      var name = dictQuantityType.getDictQuantityTypePerUserEntity()
          .getName();
      var shortcut = dictQuantityType.getDictQuantityTypePerUserEntity()
          .getShortcut();

      return new DictQuantityTypeDto(
          dictQuantityType.getId(),
          PER_USER,
          dictQuantityType.getDictQuantityTypePerUserEntity()
              .getId(),
          translate(name, language),
          translate(shortcut, language)
      );
    }
  }
}
