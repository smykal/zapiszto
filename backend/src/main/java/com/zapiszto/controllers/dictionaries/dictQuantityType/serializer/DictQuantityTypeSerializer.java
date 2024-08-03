package com.zapiszto.controllers.dictionaries.dictQuantityType.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.controllers.dictionaries.dictQuantityType.dto.DictQuantityTypeDto;
import com.zapiszto.controllers.dictionaries.dictQuantityType.entity.DictQuantityTypeEntity;
import com.zapiszto.utilities.translator.Translations;

public class DictQuantityTypeSerializer extends Translations {
  private static final String PER_USER = "PER_USER";
  private static final String BASIC = "BASIC";

  public static DictQuantityTypeDto convert(DictQuantityTypeEntity dictQuantityType, Languages language) {
    if(dictQuantityType.getDictQuantityTypeBasicEntity() != null) {
      var quantityType = dictQuantityType.getDictQuantityTypeBasicEntity().getName();

      return DictQuantityTypeDto.builder()
          .id(dictQuantityType.getId())
          .dict(BASIC)
          .dict_id(dictQuantityType.getDictQuantityTypeBasicEntity().getId())
          .name(translate(quantityType, language))
          .shortcut(dictQuantityType.getDictQuantityTypeBasicEntity().getShortcut())
          .build();
    } else {
      var quantityType = dictQuantityType.getDictQuantityTypePerUserEntity().getName();

      return DictQuantityTypeDto.builder()
          .id(dictQuantityType.getId())
          .dict(PER_USER)
          .dict_id(dictQuantityType.getDictQuantityTypePerUserEntity().getId())
          .name(translate(quantityType, language))
          .shortcut(dictQuantityType.getDictQuantityTypePerUserEntity().getShortcut())
          .build();
    }
  }
}
