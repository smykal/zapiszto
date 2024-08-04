package com.zapiszto.controllers.dictionaries.dictSessionPart.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.controllers.dictionaries.dictSessionPart.dto.DictSessionPartDto;
import com.zapiszto.controllers.dictionaries.dictSessionPart.entity.DictSessionPartEntity;
import com.zapiszto.utilities.translator.Translations;

public class DictSessionPartSerializer extends Translations implements SerializerCommon {

  public static DictSessionPartDto convert(DictSessionPartEntity dictSessionPart, Languages language) {
    if (dictSessionPart.getDictSessionPartBasicEntity() != null) {
      var name = dictSessionPart.getDictSessionPartBasicEntity()
          .getName();

      return new DictSessionPartDto(
          dictSessionPart.getId(),
          BASIC,
          dictSessionPart.getDictSessionPartBasicEntity()
              .getId(),
          translate(name, language)
      );

    } else {
      var name = dictSessionPart.getDictSessionPartBasicEntity()
          .getName();
      return new DictSessionPartDto(
          dictSessionPart.getId(),
          PER_USER,
          dictSessionPart.getDictSessionPartPerUserEntity()
              .getId(),
          translate(name, language)
      );
    }
  }
}
